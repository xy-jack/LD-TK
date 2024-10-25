package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.ExamDTO;
import com.ruoyi.exam.domain.dto.ExamOnlineRespDTO;
import com.ruoyi.exam.domain.dto.ExamReviewRespDTO;
import com.ruoyi.exam.domain.dto.ExamSaveReqDTO;
import com.ruoyi.exam.domain.entity.Exam;

import java.util.List;

/**
 * 考试业务类
 *
 * @author yd
 */
public interface IExamService extends IService<Exam> {

    /**
     * 保存考试信息
     */
    void save(ExamSaveReqDTO reqDTO);

    /**
     * 查找考试详情
     */
    ExamSaveReqDTO findDetail(String id);

    /**
     * 查找考试详情--简要信息
     */
    ExamDTO findById(String id);

    /**
     * 分页查询数据
     */
    List<ExamDTO> page(ExamDTO reqDTO);


    /**
     * 在线考试分页响应类-考生视角
     */
    List<ExamOnlineRespDTO> onlinePaging(ExamDTO reqDTO);


    /**
     * 待阅试卷列表
     */
    List<ExamReviewRespDTO> reviewPaging(ExamDTO reqDTO);
}
