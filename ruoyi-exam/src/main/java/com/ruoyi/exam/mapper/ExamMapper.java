package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.exam.domain.dto.ExamDTO;
import com.ruoyi.exam.domain.dto.ExamOnlineRespDTO;
import com.ruoyi.exam.domain.dto.ExamReviewRespDTO;
import com.ruoyi.exam.domain.entity.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 考试Mapper
*
* @author yd
*/
public interface ExamMapper extends BaseMapper<Exam> {

    /**
     * 查找分页内容
     */
    List<ExamDTO> list(@Param("query") ExamDTO query);

    /**
     * 查找分页内容
     */
    List<ExamReviewRespDTO> reviewPaging(@Param("query") ExamDTO query);

    /**
     * 在线考试分页响应类-考生视角
     */
    List<ExamOnlineRespDTO> online(@Param("query") ExamDTO query);
}
