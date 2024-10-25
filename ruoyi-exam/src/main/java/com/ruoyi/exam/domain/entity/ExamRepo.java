package com.ruoyi.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 考试题库实体类
 *
 * @author yd
 */
@Data
@TableName("tk_exam_repo")
public class ExamRepo {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 考试ID
     */
    @TableField("exam_id")
    private String examId;

    /**
     * 题库ID
     */
    @TableField("repo_id")
    private String repoId;

    /**
     * 单选题数量
     */
    @TableField("radio_count")
    private Integer radioCount;

    /**
     * 单选题分数
     */
    @TableField("radio_score")
    private Integer radioScore;

    /**
     * 多选题数量
     */
    @TableField("multi_count")
    private Integer multiCount;

    /**
     * 多选题分数
     */
    @TableField("multi_score")
    private Integer multiScore;

    /**
     * 判断题数量
     */
    @TableField("judge_count")
    private Integer judgeCount;

    /**
     * 判断题分数
     */
    @TableField("judge_score")
    private Integer judgeScore;

}
