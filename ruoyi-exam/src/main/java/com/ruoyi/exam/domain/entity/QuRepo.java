package com.ruoyi.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* 试题题库实体类
*
* @author yd
*/
@Data
@TableName("tk_qu_repo")
public class QuRepo {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 试题
     */
    //@TableField("qu_id")
    private String quId;

    /**
     * 归属题库
     */
    //@TableField("repo_id")
    private String repoId;

    /**
     * 题目类型
     */
    //@TableField("qu_type")
    private Integer quType;

    /**
     * 排序
     */
    private Integer sort;
    
}
