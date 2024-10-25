package com.ruoyi.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseModel;
import lombok.Data;

/**
 * 错题本实体类
 *
 * @author yd
 */
@Data
@TableName("tk_user_book")
public class UserBook extends BaseModel {

    private static final long serialVersionUID = 1L;

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
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 题目ID
     */
    @TableField("qu_id")
    private String quId;

    /**
     * 错误时间
     */
    @TableField("wrong_count")
    private Integer wrongCount;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 错题序号
     */
    private Integer sort;

}
