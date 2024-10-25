package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.exam.domain.dto.ExamRepoExtDTO;
import com.ruoyi.exam.domain.entity.ExamRepo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 考试题库Mapper
*
* @author yd
*/
public interface ExamRepoMapper extends BaseMapper<ExamRepo> {

    /**
     * 查找考试题库列表
     * @param examId
     * @return
     */
    List<ExamRepoExtDTO> listByExam(@Param("examId") String examId);
}
