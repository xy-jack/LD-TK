package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
 * 考试分页响应类
 *
 * @author yd
 */
@Data
//@ApiModel(value="阅卷分页响应类", description="阅卷分页响应类")
public class ExamReviewRespDTO extends ExamDTO {

    //@ApiModelProperty(value = "考试人数", required=true)
    private Integer examUser;

    //@ApiModelProperty(value = "待阅试卷", required=true)
    private Integer unreadPaper;


}
