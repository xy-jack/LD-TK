package com.ruoyi.exam.domain.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 考试记录数据传输类
 */
@Data
//@ApiModel(value="考试记录", description="考试记录")
public class UserExamDTO {

    private String id;

    //@ApiModelProperty(value = "用户ID", required=true)
    private Long userId;

    //@Dict(dictTable = "el_exam", dicText = "title", dicCode = "id")
    //@ApiModelProperty(value = "考试ID", required=true)
    private String examId;

    //@ApiModelProperty(value = "考试次数", required=true)
    private Integer tryCount;

    //@ApiModelProperty(value = "最高分数", required=true)
    private Integer maxScore;

    //@ApiModelProperty(value = "是否通过", required=true)
    private Boolean passed;

    //@ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    //@ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
