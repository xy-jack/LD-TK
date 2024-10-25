package com.ruoyi.exam.domain.dto;


import lombok.Data;

/**
* 试卷请求类
*
* @author 聪明笨狗
*/
@Data
//@ApiModel(value="试卷列表响应类", description="试卷列表响应类")
public class PaperListRespDTO extends PaperDTO {

    //@ApiModelProperty(value = "人员", required=true)
    private String realName;

    
}
