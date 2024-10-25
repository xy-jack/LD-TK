package com.ruoyi.exam.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* 问题题目请求类
*
* @author yd
*/
@Data
public class QuQueryReqDTO {

    /**
     * 题目类型
     */
    private Integer quType;

    /**
     * 归属题库
     */
    private List<String> repoIds;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 排除ID列表
     */
    private List<String> excludes;

    
}
