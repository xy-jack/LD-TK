package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.entity.ExamDepart;

import java.util.List;

/**
 * 考试部门业务类
 *
 * @author yd
 */
public interface IExamDepartService extends IService<ExamDepart> {

    /**
     * 保存全部
     */
    void saveAll(String examId, List<String> departs);


    /**
     * 根据考试查找对应的部门
     */
    List<String> listByExam(String examId);
}
