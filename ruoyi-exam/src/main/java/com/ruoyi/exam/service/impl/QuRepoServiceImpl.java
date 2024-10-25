package com.ruoyi.exam.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.exam.domain.dto.QuRepoBatchReqDTO;
import com.ruoyi.exam.domain.dto.QuRepoDTO;
import com.ruoyi.exam.domain.entity.Qu;
import com.ruoyi.exam.domain.entity.QuRepo;
import com.ruoyi.exam.mapper.QuMapper;
import com.ruoyi.exam.mapper.QuRepoMapper;
import com.ruoyi.exam.service.IQuRepoService;
import com.ruoyi.exam.service.IRepoService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class QuRepoServiceImpl extends ServiceImpl<QuRepoMapper, QuRepo> implements IQuRepoService {


    @Autowired
    private QuMapper quMapper;

    @Autowired
    private IRepoService repoService;

    @Override
    public List<QuRepoDTO> list(QuRepoDTO reqDTO) {

        //创建分页对象
        /*IPage<QuRepo> query = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());

        //查询条件
        QueryWrapper<QuRepo> wrapper = new QueryWrapper<>();

        //获得数据
        IPage<QuRepo> page = this.page(query, wrapper);
        //转换结果
        IPage<QuRepoDTO> pageData = JSON.parseObject(JSON.toJSONString(page), new TypeReference<Page<QuRepoDTO>>() {
        });
        return pageData;*/
        return null;
    }

    @Override
    public void saveAll(String quId, Integer quType, List<String> ids) {
        // 先删除
        QueryWrapper<QuRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(QuRepo::getQuId, quId);
        this.remove(wrapper);

        // 保存全部
        if (!CollectionUtils.isEmpty(ids)) {
            List<QuRepo> list = new ArrayList<>();
            for (String id : ids) {
                QuRepo ref = new QuRepo();
                ref.setQuId(quId);
                ref.setRepoId(id);
                ref.setQuType(quType);
                list.add(ref);
            }
            this.saveBatch(list);


            for (String id : ids) {
                this.sortRepo(id);
            }
        }


    }

    @Override
    public List<String> listByQu(String quId) {
        // 先删除
        QueryWrapper<QuRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(QuRepo::getQuId, quId);
        List<QuRepo> list = this.list(wrapper);
        List<String> ids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (QuRepo item : list) {
                ids.add(item.getRepoId());
            }
        }
        return ids;
    }

    @Override
    public List<String> listByRepo(String repoId, Integer quType, boolean rand) {
        QueryWrapper<QuRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(QuRepo::getRepoId, repoId);

        if (quType != null) {
            wrapper.lambda().eq(QuRepo::getQuType, quType);
        }

        if (rand) {
            wrapper.orderByAsc(" RAND() ");
        } else {
            wrapper.lambda().orderByAsc(QuRepo::getSort);
        }

        List<QuRepo> list = this.list(wrapper);
        List<String> ids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (QuRepo item : list) {
                ids.add(item.getQuId());
            }
        }
        return ids;
    }

    @Override
    public void batchAction(QuRepoBatchReqDTO reqDTO) {
        // 移除的
        if (reqDTO.getRemove() != null && reqDTO.getRemove()) {
            QueryWrapper<QuRepo> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                    .in(QuRepo::getRepoId, reqDTO.getRepoIds())
                    .in(QuRepo::getQuId, reqDTO.getQuIds());
            this.remove(wrapper);
        } else {

            // 新增的
            for (String quId : reqDTO.getQuIds()) {
                Qu q = quMapper.selectById(quId);
                this.saveAll(quId, q.getQuType(), reqDTO.getRepoIds());
            }
        }

        for (String id : reqDTO.getRepoIds()) {
            this.sortRepo(id);
        }

    }


    /**
     * 单个题库进行排序
     *
     * @param repoId
     */
    private void sortRepo(String repoId) {

        QueryWrapper<QuRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(QuRepo::getRepoId, repoId);

        List<QuRepo> list = this.list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        int sort = 1;
        for (QuRepo item : list) {
            item.setSort(sort);
            sort++;
        }
        this.updateBatchById(list);
    }
}
