package com.ruoyi.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.exam.domain.dto.UserExamReqDTO;
import com.ruoyi.exam.domain.dto.UserExamRespDTO;
import com.ruoyi.exam.domain.entity.UserExam;

import java.util.List;


/**
* 考试记录业务类
*
* @author yd
*/
public interface IUserExamService extends IService<UserExam> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    List<UserExamRespDTO> paging(UserExamReqDTO reqDTO);

    /**
     * 分页查询数据
     * @param reqDTO
     * @return
     */
    List<UserExamRespDTO> myPaging(UserExamReqDTO reqDTO);


    /**
     * 考试完成后加入成绩
     * @param userId
     * @param examId
     * @param score
     * @param passed
     */
    void joinResult(Long userId, String examId, Integer score, boolean passed);
}
