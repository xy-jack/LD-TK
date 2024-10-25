package com.ruoyi.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ruoyi.common.core.domain.BaseModel;
import lombok.Data;

import java.util.Date;

/**
* 问题题目实体类
*
* @author yd
*/
@Data
@TableName("tk_qu")
public class Qu extends BaseModel {

    /**
     * 题目ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 题目类型
     */
    //@TableField("qu_type")
    private Integer quType;

    /**
     * 1普通,2较难
     */
    private Integer level;

    /**
     * 题目图片
     */
    private String image;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 题目备注
     */
    private String remark;

    /**
     * 整题解析
     */
    private String analysis;
    
}
