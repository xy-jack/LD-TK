package com.ruoyi.exam.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.exam.domain.dto.QuAnswerDTO;
import com.ruoyi.exam.domain.entity.QuAnswer;
import com.ruoyi.exam.mapper.QuAnswerMapper;
import com.ruoyi.exam.service.IQuAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 语言设置 服务实现类
 *
 * @author yd
 */
@Service
public class QuAnswerServiceImpl extends ServiceImpl<QuAnswerMapper, QuAnswer> implements IQuAnswerService {

    @Override
    public List<QuAnswerDTO> selectByCondition(QuAnswerDTO reqDTO) {
        List<QuAnswerDTO> result = new ArrayList<>();

        QueryWrapper<QuAnswer> wrapper = new QueryWrapper<>();
        List<QuAnswer> list = this.list(wrapper);
        list.forEach(li -> {
            QuAnswerDTO dto = new QuAnswerDTO();
            BeanUtils.copyProperties(li, dto);
        });
        return result;
    }

    @Override
    public List<QuAnswer> listAnswerByRandom(String quId) {
        QueryWrapper<QuAnswer> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(QuAnswer::getQuId, quId);
        wrapper.last(" ORDER BY RAND() ");

        return this.list(wrapper);
    }

    @Override
    public List<QuAnswerDTO> listByQu(String quId) {
        List<QuAnswerDTO> result = new ArrayList<>();

        QueryWrapper<QuAnswer> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(QuAnswer::getQuId, quId);

        List<QuAnswer> list = this.list(wrapper);
        list.forEach(li -> {
            QuAnswerDTO dto = new QuAnswerDTO();
            BeanUtils.copyProperties(li, dto);
            result.add(dto);
        });

        return result;
    }


    /**
     * 查找已存在的列表
     *
     * @param quId
     * @return
     */
    public List<String> findExistsList(String quId) {
        //返回结果
        List<String> ids = new ArrayList<>();

        QueryWrapper<QuAnswer> wrapper = new QueryWrapper();
        wrapper.lambda().eq(QuAnswer::getQuId, quId);
        List<QuAnswer> list = this.list(wrapper);

        if (!CollectionUtils.isEmpty(list)) {
            for (QuAnswer item : list) {
                ids.add(item.getId());
            }
        }
        return ids;
    }

    @Override
    public void saveAll(String quId, List<QuAnswerDTO> list) {

        //最终要保存的列表
        List<QuAnswer> saveList = new ArrayList<>();

        //已存在的标签列表
        List<String> ids = this.findExistsList(quId);

        if (!CollectionUtils.isEmpty(list)) {
            for (QuAnswerDTO item : list) {

                //标签ID
                String id = item.getId();
                QuAnswer answer = new QuAnswer();
                BeanUtils.copyProperties(item, answer);
                answer.setQuId(quId);

                //补全ID避免新增
                if (ids.contains(id)) {
                    ids.remove(id);
                }

                saveList.add(answer);
            }

            //保存标签列表
            if (!CollectionUtils.isEmpty(saveList)) {
                this.saveOrUpdateBatch(saveList);
            }

            //删除已移除
            if (!ids.isEmpty()) {
                this.removeByIds(ids);
            }
        } else {

            QueryWrapper<QuAnswer> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(QuAnswer::getQuId, quId);
            this.remove(wrapper);
        }
    }


}
