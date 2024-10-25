package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
 * 候选答案请求类
 *
 * @author yd
 */
@Data
//@ApiModel(value="候选答案", description="候选答案")
public class QuAnswerDTO {

    //@ApiModelProperty(value = "答案ID", required=true)
    private String id;

    //@ApiModelProperty(value = "问题ID", required=true)
    private String quId;

    //@ApiModelProperty(value = "是否正确", required=true)
    private Boolean isRight;

    //@ApiModelProperty(value = "选项图片", required=true)
    private String image;

    //@ApiModelProperty(value = "答案内容", required=true)
    private String content;

    //@ApiModelProperty(value = "答案分析", required=true)
    private String analysis;

}
