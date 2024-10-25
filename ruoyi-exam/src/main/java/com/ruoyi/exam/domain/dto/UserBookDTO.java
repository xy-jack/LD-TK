package com.ruoyi.exam.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
* 错题本请求类
*
* @author yd
*/
@Data
//@ApiModel(value="错题本", description="错题本")
public class UserBookDTO  {

    //@ApiModelProperty(value = "ID", required=true)
    private String id;

    //@ApiModelProperty(value = "考试ID", required=true)
    private String examId;

    //@ApiModelProperty(value = "用户ID", required=true)
    private String userId;

    //@ApiModelProperty(value = "题目ID", required=true)
    private String quId;

    //@ApiModelProperty(value = "加入时间", required=true)
    private LocalDateTime createTime;

    //@ApiModelProperty(value = "最近错误时间", required=true)
    private LocalDateTime updateTime;

    //@ApiModelProperty(value = "错误时间", required=true)
    private Integer wrongCount;

    //@ApiModelProperty(value = "题目标题", required=true)
    private String title;

    //@ApiModelProperty(value = "错题序号", required=true)
    private Integer sort;

}