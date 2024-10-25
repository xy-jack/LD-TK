package com.ruoyi.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ruoyi.common.core.domain.BaseModel;
import lombok.Data;

import java.util.Date;

/**
* 考试实体类
*
* @author yd
*/
@Data
@TableName("tk_exam")
public class Exam extends BaseModel {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 考试名称
     */
    private String title;

    /**
     * 考试描述
     */
    private String content;

    /**
     * 1公开2部门3定员
     */
    @TableField("open_type")
    private Integer openType;

    /**
     * 考试状态
     */
    private Integer state;

    /**
     * 是否限时
     */
    @TableField("time_limit")
    private Boolean timeLimit;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 总分数
     */
    @TableField("total_score")
    private Integer totalScore;

    /**
     * 总时长（分钟）
     */
    @TableField("total_time")
    private Integer totalTime;

    /**
     * 及格分数
     */
    @TableField("qualify_score")
    private Integer qualifyScore;
    
}
