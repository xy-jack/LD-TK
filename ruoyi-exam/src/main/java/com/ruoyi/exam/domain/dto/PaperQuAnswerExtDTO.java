package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
 * 试卷考题备选答案请求类
 *
 * @author yd
 */
@Data
//@ApiModel(value="试卷考题备选答案", description="试卷考题备选答案")
public class PaperQuAnswerExtDTO extends PaperQuAnswerDTO {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "试题图片", required=true)
    private String image;

    //@ApiModelProperty(value = "答案内容", required=true)
    private String content;


}
