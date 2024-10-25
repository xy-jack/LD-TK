package com.ruoyi.exam.domain.dto;

import com.ruoyi.exam.domain.entity.Exam;
import lombok.Data;

import java.util.List;

/**
 * 考试保存请求类
 *
 * @author yd
 */
@Data
//@ApiModel(value = "考试保存请求类", description = "考试保存请求类")
public class ExamSaveReqDTO extends Exam {

    //@ApiModelProperty(value = "题库列表", required = true)
    private List<ExamRepoExtDTO> repoList;

    //@ApiModelProperty(value = "考试部门列表", required = true)
    private List<String> departIds;

}
