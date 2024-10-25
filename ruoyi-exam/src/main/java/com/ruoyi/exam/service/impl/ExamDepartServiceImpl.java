package com.ruoyi.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.exam.domain.entity.ExamDepart;
import com.ruoyi.exam.mapper.ExamDepartMapper;
import com.ruoyi.exam.service.IExamDepartService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试部门业务实现类
 *
 * @author yd
 */
@Service
public class ExamDepartServiceImpl extends ServiceImpl<ExamDepartMapper, ExamDepart> implements IExamDepartService {

    @Override
    public void saveAll(String examId, List<String> departs) {

        // 先删除
        QueryWrapper<ExamDepart> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamDepart::getExamId, examId);
        this.remove(wrapper);

        // 再增加
        if (CollectionUtils.isEmpty(departs)) {
            throw new ServiceException("请至少选择选择一个部门！！");
        }
        List<ExamDepart> list = new ArrayList<>();

        for (String id : departs) {
            ExamDepart depart = new ExamDepart();
            depart.setDepartId(id);
            depart.setExamId(examId);
            list.add(depart);
        }

        this.saveBatch(list);
    }

    @Override
    public List<String> listByExam(String examId) {
        // 先删除
        QueryWrapper<ExamDepart> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamDepart::getExamId, examId);
        List<ExamDepart> list = this.list(wrapper);
        List<String> ids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ExamDepart item : list) {
                ids.add(item.getDepartId());
            }
        }

        return ids;

    }
}
