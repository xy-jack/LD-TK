package com.ruoyi.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 考试部门实体类
 *
 * @author yd
 */
@Data
@TableName("tk_exam_depart")
public class ExamDepart {

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
     * 部门ID
     */
    @TableField("depart_id")
    private String departId;

}
