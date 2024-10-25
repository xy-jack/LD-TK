package com.ruoyi.exam.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 问题题目请求类
 *
 * @author yd
 */
@Data
//@ApiModel(value="试题题库批量操作类", description="试题题库批量操作类")
public class QuRepoBatchReqDTO {


    //@ApiModelProperty(value = "题目ID", required=true)
    private List<String> quIds;

    //@ApiModelProperty(value = "题目类型", required=true)
    private List<String> repoIds;

    //@ApiModelProperty(value = "是否移除，否就新增；是就移除", required=true)
    private Boolean remove;

}
