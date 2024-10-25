package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.ExamRepoExtDTO;
import com.ruoyi.exam.domain.entity.ExamRepo;

import java.util.List;

/**
* 考试题库业务类
*
* @author yd
*/
public interface IExamRepoService extends IService<ExamRepo> {

    /**
     * 保存全部
     */
    void saveAll(String examId, List<ExamRepoExtDTO> list);

    /**
     * 查找考试题库列表
     */
    List<ExamRepoExtDTO> listByExam(String examId);

    /**
     * 清理脏数据
     */
    void clear(String examId);

}
