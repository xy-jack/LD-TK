package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
* 试卷请求类
*
* @author yd
*/
@Data
//@ApiModel(value="试卷", description="试卷")
public class PaperListReqDTO {

    //@ApiModelProperty(value = "用户ID", required=true)
    private Long userId;

    //@ApiModelProperty(value = "部门ID", required=true)
    private String departId;

    //@ApiModelProperty(value = "规则ID", required=true)
    private String examId;

    //@ApiModelProperty(value = "用户昵称", required=true)
    private String realName;

    //@ApiModelProperty(value = "试卷状态", required=true)
    private Integer state;
}
