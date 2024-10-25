package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
* 考试记录数据传输类
*/
@Data
//@ApiModel(value="考试记录", description="考试记录")
public class UserExamReqDTO extends UserExamDTO {

    //@ApiModelProperty(value = "考试名称", required=true)
    private String title;

    //@ApiModelProperty(value = "人员名称", required=true)
    private String realName;


}
