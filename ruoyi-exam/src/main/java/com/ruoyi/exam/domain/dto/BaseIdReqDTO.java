package com.ruoyi.exam.domain.dto;

import lombok.Data;

/**
 * 主键通用请求类，用于根据ID查询
 *
 * @author yd
 */
@Data
//@ApiModel(value="主键通用请求类", description="主键通用请求类")
public class BaseIdReqDTO {


    //@ApiModelProperty(value = "主键ID", required=true)
    private String id;

    //@JsonIgnore
    private String userId;

}
