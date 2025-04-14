package com.ruoyi.exam.domain.vo;

import lombok.Data;

@Data
public class QuVO {

    /**
     * 题目ID
     */
    private String id;

    /**
     * 题目类型
     */
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

    /**
     * 题库名称
     */
    private String title;
}
