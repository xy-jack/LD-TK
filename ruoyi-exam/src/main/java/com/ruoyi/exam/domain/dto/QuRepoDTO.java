package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
* 试题题库请求类
*
* @author yd
*/
@Data
//@ApiModel(value="试题题库", description="试题题库")
public class QuRepoDTO {

    private String id;

    //@ApiModelProperty(value = "试题", required=true)
    private String quId;

    //@ApiModelProperty(value = "归属题库", required=true)
    private String repoId;

    //@ApiModelProperty(value = "题目类型", required=true)
    private Integer quType;

    //@ApiModelProperty(value = "排序", required=true)
    private Integer sort;

}