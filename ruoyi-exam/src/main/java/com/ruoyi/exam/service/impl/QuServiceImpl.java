package com.ruoyi.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.QuType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.exam.domain.dto.QuAnswerDTO;
import com.ruoyi.exam.domain.dto.QuDTO;
import com.ruoyi.exam.domain.dto.QuDetailDTO;
import com.ruoyi.exam.domain.dto.QuQueryReqDTO;
import com.ruoyi.exam.domain.dto.excel.QuExportDTO;
import com.ruoyi.exam.domain.entity.Qu;
import com.ruoyi.exam.domain.entity.QuAnswer;
import com.ruoyi.exam.domain.entity.QuRepo;
import com.ruoyi.exam.mapper.QuMapper;
import com.ruoyi.exam.service.IQuAnswerService;
import com.ruoyi.exam.service.IQuRepoService;
import com.ruoyi.exam.service.IQuService;
import com.ruoyi.exam.service.IRepoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 语言设置 服务实现类
 *
 * @author yd
 */
@Service
public class QuServiceImpl extends ServiceImpl<QuMapper, Qu> implements IQuService {

    @Autowired
    private IQuAnswerService quAnswerService;

    @Autowired
    private IQuRepoService quRepoService;

    @Autowired
    private IRepoService repoService;

    @Override
    public List<QuDTO> list(QuQueryReqDTO reqDTO) {
        PageUtils.startPage();
        //转换结果
        return baseMapper.selectQuList(reqDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> ids) {
        // 移除题目
        this.removeByIds(ids);

        // 移除选项
        QueryWrapper<QuAnswer> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(QuAnswer::getQuId, ids);
        quAnswerService.remove(wrapper);

        // 移除题库绑定
        QueryWrapper<QuRepo> wrapper1 = new QueryWrapper<>();
        wrapper1.lambda().in(QuRepo::getQuId, ids);
        quRepoService.remove(wrapper1);
    }

    @Override
    public List<Qu> listByRandom(String repoId, Integer quType, List<String> excludes, Integer size) {
        return baseMapper.listByRandom(repoId, quType, excludes, size);
    }

    @Override
    public QuDetailDTO detail(String id) {

        QuDetailDTO respDTO = new QuDetailDTO();
        Qu qu = this.getById(id);
        BeanUtils.copyProperties(qu, respDTO);

        List<QuAnswerDTO> answerList = quAnswerService.listByQu(id);
        respDTO.setAnswerList(answerList);

        List<String> repoIds = quRepoService.listByQu(id);
        respDTO.setRepoIds(repoIds);

        return respDTO;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(QuDetailDTO reqDTO) {


        // 校验数据
        this.checkData(reqDTO, "");

        Qu qu = new Qu();
        BeanUtils.copyProperties(reqDTO, qu);

        // 更新
        this.saveOrUpdate(qu);

        // 保存全部问题
        quAnswerService.saveAll(qu.getId(), reqDTO.getAnswerList());

        // 保存到题库
        quRepoService.saveAll(qu.getId(), qu.getQuType(), reqDTO.getRepoIds());

    }

    @Override
    public List<QuExportDTO> listForExport(QuQueryReqDTO query) {
        return baseMapper.listForExport(query);
    }

    @Override
    public int importExcel(List<QuExportDTO> dtoList) {

        //根据题目名称分组
        Map<Integer, List<QuExportDTO>> anMap = new HashMap<>(16);

        //题目本体信息
        Map<Integer, QuExportDTO> quMap = new HashMap<>(16);

        //数据分组
        for (QuExportDTO item : dtoList) {

            // 空白的ID
            if (StringUtils.isEmpty(item.getNo())) {
                continue;
            }

            Integer key;
            //序号
            try {
                key = Integer.parseInt(item.getNo());
            } catch (Exception e) {
                continue;
            }

            //如果已经有题目了，直接处理选项
            if (anMap.containsKey(key)) {
                anMap.get(key).add(item);
            } else {
                //如果没有，将题目内容和选项一起
                List<QuExportDTO> subList = new ArrayList<>();
                subList.add(item);
                anMap.put(key, subList);
                quMap.put(key, item);
            }
        }

        int count = 0;
        try {

            //循环题目插入
            for (Integer key : quMap.keySet()) {

                QuExportDTO im = quMap.get(key);

                //题目基本信息
                QuDetailDTO qu = new QuDetailDTO();
                qu.setContent(im.getQContent());
                qu.setAnalysis(im.getQAnalysis());
                qu.setQuType(Integer.parseInt(im.getQuType()));
                qu.setCreateTime(LocalDateTime.now());

                //设置回答列表
                List<QuAnswerDTO> answerList = this.processAnswerList(anMap.get(key));
                //设置题目
                qu.setAnswerList(answerList);
                //设置引用题库
                qu.setRepoIds(im.getRepoList());
                // 保存答案
                this.save(qu);
                count++;
            }

        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServiceException("导入出现问题，行：" + count + "，" + e.getMessage());
        }

        return count;
    }

    /**
     * 处理回答列表
     *
     * @param importList
     * @return
     */
    private List<QuAnswerDTO> processAnswerList(List<QuExportDTO> importList) {

        List<QuAnswerDTO> list = new ArrayList<>(16);
        for (QuExportDTO item : importList) {
            QuAnswerDTO a = new QuAnswerDTO();
            a.setIsRight("1".equals(item.getAIsRight()));
            a.setContent(item.getAContent());
            a.setAnalysis(item.getAAnalysis());
            a.setId("");
            list.add(a);
        }
        return list;
    }

    /**
     * 校验题目信息
     *
     * @param qu
     * @param no
     * @throws Exception
     */
    public void checkData(QuDetailDTO qu, String no) {


        if (StringUtils.isEmpty(qu.getContent())) {
            throw new ServiceException(no + "题目内容不能为空！");
        }


        if (CollectionUtils.isEmpty(qu.getRepoIds())) {
            throw new ServiceException(no + "至少要选择一个题库！");
        }

        List<QuAnswerDTO> answers = qu.getAnswerList();


            if (CollectionUtils.isEmpty(answers)) {
                throw new ServiceException(no + "客观题至少要包含一个备选答案！");
            }


            int trueCount = 0;
            for (QuAnswerDTO a : answers) {

                if (a.getIsRight() == null) {
                    throw new ServiceException(no + "必须定义选项是否正确项！");
                }

                if (StringUtils.isEmpty(a.getContent())) {
                    throw new ServiceException(no + "选项内容不为空！");
                }

                if (a.getIsRight()) {
                    trueCount += 1;
                }
            }

            if (trueCount == 0) {
                throw new ServiceException(no + "至少要包含一个正确项！");
            }


            //单选题
            if (qu.getQuType().equals(QuType.RADIO) && trueCount > 1) {
                throw new ServiceException(no + "单选题不能包含多个正确项！");
            }

    }
}
