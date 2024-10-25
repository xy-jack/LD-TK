package com.ruoyi.exam.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
//@ApiModel(value="试卷创建请求类", description="试卷创建请求类")
public class PaperCreateReqDTO  {

    @JsonIgnore
    private String userId;

    //@ApiModelProperty(value = "考试ID", required=true)
    private String examId;

}
