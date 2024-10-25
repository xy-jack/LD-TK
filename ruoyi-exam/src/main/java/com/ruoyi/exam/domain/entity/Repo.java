package com.ruoyi.exam.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 题库实体类
*
* @author yd
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tk_repo")
public class Repo extends BaseModel {

    /**
     * 题库ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 题库编号
     */
    private String code;

    /**
     * 题库名称
     */
    private String title;

    /**
     * 题库备注
     */
    private String remark;

}
