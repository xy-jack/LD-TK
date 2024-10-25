package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
 * 考试题库数据传输类
 *
 * @author yd
 */
@Data
//@ApiModel(value="考试题库扩展响应类", description="考试题库扩展响应类")
public class ExamRepoExtDTO extends ExamRepoDTO {

    //@ApiModelProperty(value = "单选题总量", required=true)
    private Integer totalRadio;

    //@ApiModelProperty(value = "多选题总量", required=true)
    private Integer totalMulti;

    //@ApiModelProperty(value = "判断题总量", required=true)
    private Integer totalJudge;

}
