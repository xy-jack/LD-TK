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
* 考试记录实体类
*/
@Data
@TableName("tk_user_exam")
public class UserExam extends BaseModel {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    
    /**
    * 用户ID
    */
    @TableField("user_id")
    private Long userId;
    
    /**
    * 考试ID
    */
    @TableField("exam_id")
    private String examId;
    
    /**
    * 考试次数
    */
    @TableField("try_count")
    private Integer tryCount;
    
    /**
    * 最高分数
    */
    @TableField("max_score")
    private Integer maxScore;
    
    /**
    * 是否通过
    */
    private Boolean passed;

}
