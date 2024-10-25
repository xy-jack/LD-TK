package com.ruoyi.exam.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.BeanMapper;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.exam.domain.dto.PaperQuDTO;
import com.ruoyi.exam.domain.dto.PaperQuDetailDTO;
import com.ruoyi.exam.domain.entity.PaperQu;
import com.ruoyi.exam.mapper.PaperQuMapper;
import com.ruoyi.exam.service.IPaperQuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 语言设置 服务实现类
*
* @author yd
*/
@Service
public class PaperQuServiceImpl extends ServiceImpl<PaperQuMapper, PaperQu> implements IPaperQuService {

    @Override
    public List<PaperQuDTO> paging(PaperQuDTO reqDTO) {
        //查询条件
        QueryWrapper<PaperQu> wrapper = new QueryWrapper<>();

        //开始分页
        PageUtils.startPage();
        //获得数据
        List<PaperQu> list = this.list(wrapper);
        //转换结果
        return BeanMapper.mapList(list, PaperQuDTO.class);
     }

    @Override
    public List<PaperQuDTO> listByPaper(String paperId) {

        //查询条件
        QueryWrapper<PaperQu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PaperQu::getPaperId, paperId)
                .orderByAsc(PaperQu::getSort);

        List<PaperQu> list = this.list(wrapper);
        return BeanMapper.mapList(list, PaperQuDTO.class);
    }

    @Override
    public PaperQu findByKey(String paperId, String quId) {
        //查询条件
        QueryWrapper<PaperQu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PaperQu::getPaperId, paperId)
                .eq(PaperQu::getQuId, quId);

        return this.getOne(wrapper, false);
    }

    @Override
    public void updateByKey(PaperQu qu) {

        //查询条件
        QueryWrapper<PaperQu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PaperQu::getPaperId, qu.getPaperId())
                .eq(PaperQu::getQuId, qu.getQuId());

        this.update(qu, wrapper);
    }

    @Override
    public int sumObjective(String paperId) {
        return baseMapper.sumObjective(paperId);
    }

    @Override
    public int sumSubjective(String paperId) {
        return baseMapper.sumSubjective(paperId);
    }

    @Override
    public List<PaperQuDetailDTO> listForPaperResult(String paperId) {
        return baseMapper.listByPaper(paperId);
    }
}
