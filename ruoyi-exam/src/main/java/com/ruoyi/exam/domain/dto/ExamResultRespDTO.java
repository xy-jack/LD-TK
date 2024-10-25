package com.ruoyi.exam.domain.dto;

import lombok.Data;

import java.util.List;

@Data
//@ApiModel(value="考试结果展示响应类", description="考试结果展示响应类")
public class ExamResultRespDTO extends PaperDTO {

    //@ApiModelProperty(value = "问题列表", required=true)
    private List<PaperQuDetailDTO> quList;

}
