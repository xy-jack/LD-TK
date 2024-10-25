package com.ruoyi.exam.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用状态请求类，用于修改状态什么的
 *
 * @author yd
 */
@Data
//@ApiModel(value="通用状态请求类", description="通用状态请求类")
@AllArgsConstructor
@NoArgsConstructor
public class BaseStateReqDTO {


    //@ApiModelProperty(value = "要修改对象的ID列表", required=true)
    private List<String> ids;

    //@ApiModelProperty(value = "通用状态，0为正常，1为禁用", required=true)
    private Integer state;
}
