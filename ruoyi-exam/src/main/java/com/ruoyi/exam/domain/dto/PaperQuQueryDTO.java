package com.ruoyi.exam.domain.dto;

import lombok.Data;

@Data
//@ApiModel(value="查找试卷题目详情请求类", description="查找试卷题目详情请求类")
public class PaperQuQueryDTO {

    //@ApiModelProperty(value = "试卷ID", required=true)
    private String paperId;

    //@ApiModelProperty(value = "题目ID", required=true)
    private String quId;

}
