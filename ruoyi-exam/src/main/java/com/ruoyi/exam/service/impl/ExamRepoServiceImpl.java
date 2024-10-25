package com.ruoyi.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.exam.domain.dto.ExamRepoExtDTO;
import com.ruoyi.exam.domain.entity.ExamRepo;
import com.ruoyi.exam.mapper.ExamRepoMapper;
import com.ruoyi.exam.service.IExamRepoService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试题库业务实现类
 *
 * @author yd
 */
@Service
public class ExamRepoServiceImpl extends ServiceImpl<ExamRepoMapper, ExamRepo> implements IExamRepoService {


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAll(String examId, List<ExamRepoExtDTO> list) {

        // 先删除
        QueryWrapper<ExamRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamRepo::getExamId, examId);
        this.remove(wrapper);

        // 再增加
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("必须选择题库！");
        }
        List<ExamRepo> repos = new ArrayList<>();

        //List<ExamRepo> repos = BeanMapper.mapList(list, ExamRepo.class);
        for (ExamRepoExtDTO dto : list) {
            ExamRepo examRepo = new ExamRepo();
            BeanUtils.copyProperties(dto, examRepo);
            examRepo.setExamId(examId);
            examRepo.setId(IdWorker.getIdStr());

            repos.add(examRepo);
        }

        this.saveBatch(repos);
    }

    @Override
    public List<ExamRepoExtDTO> listByExam(String examId) {
        return baseMapper.listByExam(examId);
    }

    @Override
    public void clear(String examId) {

        // 先删除
        QueryWrapper<ExamRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamRepo::getExamId, examId);
        this.remove(wrapper);
    }


}
