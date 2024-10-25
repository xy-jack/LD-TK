package com.ruoyi.exam.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ruoyi.common.utils.BeanMapper;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.exam.domain.dto.PaperQuAnswerDTO;
import com.ruoyi.exam.domain.dto.PaperQuAnswerExtDTO;
import com.ruoyi.exam.domain.entity.PaperQuAnswer;
import com.ruoyi.exam.mapper.PaperQuAnswerMapper;
import com.ruoyi.exam.service.IPaperQuAnswerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 语言设置 服务实现类
*
* @author yd
*/
@Service
public class PaperQuAnswerServiceImpl extends ServiceImpl<PaperQuAnswerMapper, PaperQuAnswer> implements IPaperQuAnswerService {

    @Override
    public List<PaperQuAnswerDTO> paging(PaperQuAnswerDTO reqDTO) {
        //查询条件
        QueryWrapper<PaperQuAnswer> wrapper = new QueryWrapper<>();
        PageUtils.startPage();
        //获得数据
        List<PaperQuAnswer> list = this.list(wrapper);
        //转换结果
        return BeanMapper.mapList(list, PaperQuAnswerDTO.class);
     }

    @Override
    public List<PaperQuAnswerExtDTO> listForExam(String paperId, String quId) {
        return baseMapper.list(paperId, quId);
    }

    @Override
    public List<PaperQuAnswer> listForFill(String paperId, String quId) {
        //查询条件
        QueryWrapper<PaperQuAnswer> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(PaperQuAnswer::getPaperId, paperId)
                .eq(PaperQuAnswer::getQuId, quId);

        return this.list(wrapper);
    }
}
