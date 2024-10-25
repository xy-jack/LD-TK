package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.ruoyi.exam.domain.dto.UserExamReqDTO;
import com.ruoyi.exam.domain.dto.UserExamRespDTO;
import com.ruoyi.exam.domain.entity.UserExam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 考试记录Mapper
*/
public interface UserExamMapper extends BaseMapper<UserExam> {

    /**
     * 我的考试分页
     * @param page
     * @param query
     * @return
     */
    List<UserExamRespDTO> paging(@Param("query") UserExamReqDTO query);

}
