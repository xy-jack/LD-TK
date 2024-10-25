package com.ruoyi.exam.domain.dto;

import lombok.Data;

import java.util.List;

/**
* 试卷考题请求类
*
* @author yd
*/
@Data
//@ApiModel(value="试卷题目详情类", description="试卷题目详情类")
public class PaperQuDetailDTO extends PaperQuDTO {

    //@ApiModelProperty(value = "图片", required=true)
    private String image;

    //@ApiModelProperty(value = "题目内容", required=true)
    private String content;

    //@ApiModelProperty(value = "答案内容", required=true)
    List<PaperQuAnswerExtDTO> answerList;
}
