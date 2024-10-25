package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.*;
import com.ruoyi.exam.domain.entity.Paper;

import java.util.List;


/**
 * 试卷业务类
 *
 * @author yd
 */
public interface IPaperService extends IService<Paper> {

    /**
     * 创建试卷
     *
     * @param userId
     * @param examId
     * @return
     */
    String createPaper(Long userId, String examId);


    /**
     * 查找详情
     *
     * @param paperId
     * @return
     */
    ExamDetailRespDTO paperDetail(String paperId);

    /**
     * 考试结果
     *
     * @param paperId
     * @return
     */
    ExamResultRespDTO paperResult(String paperId);

    /**
     * 查找题目详情
     *
     * @param paperId
     * @param quId
     * @return
     */
    PaperQuDetailDTO findQuDetail(String paperId, String quId);

    /**
     * 填充答案
     *
     * @param reqDTO
     */
    void fillAnswer(PaperAnswerDTO reqDTO);

    /**
     * 交卷操作
     *
     * @param paperId
     * @return
     */
    void handExam(String paperId);

    /**
     * 试卷列表响应类
     *
     * @param reqDTO
     * @return
     */
    List<PaperListRespDTO> paging(PaperListReqDTO reqDTO);

    /**
     * 检测是否有进行中的考试
     *
     * @param userId
     * @return
     */
    PaperDTO checkProcess(Long userId);

}
