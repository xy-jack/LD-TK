package com.ruoyi.exam.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.enums.ExamState;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 考试数据传输类
 *
 * @author yd
 */
@Data
//@ApiModel(value = "考试", description = "考试")
public class ExamDTO {

    //@ApiModelProperty(value = "ID", required = true)
    private String id;

    //@ApiModelProperty(value = "考试名称", required = true)
    private String title;

    //@ApiModelProperty(value = "考试描述", required = true)
    private String content;

    //@ApiModelProperty(value = "1公开2部门3定员", required = true)
    private Integer openType;

    //@ApiModelProperty(value = "考试状态", required = true)
    private Integer state;

    //@ApiModelProperty(value = "是否限时", required = true)
    private Boolean timeLimit;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ApiModelProperty(value = "开始时间", required = true)
    private Date startTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ApiModelProperty(value = "结束时间", required = true)
    private Date endTime;

    //@ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    //@ApiModelProperty(value = "更新时间", required = true)
    private LocalDateTime updateTime;

    //@ApiModelProperty(value = "总分数", required = true)
    private Integer totalScore;

    //@ApiModelProperty(value = "总时长（分钟）", required = true)
    private Integer totalTime;

    //@ApiModelProperty(value = "及格分数", required = true)
    private Integer qualifyScore;


    /**
     * 是否结束
     *
     * @return
     */
    public Integer getState() {

        if (this.timeLimit != null && this.timeLimit) {

            if (System.currentTimeMillis() < startTime.getTime()) {
                return ExamState.READY_START;
            }

            if (System.currentTimeMillis() > endTime.getTime()) {
                return ExamState.OVERDUE;
            }

            if (System.currentTimeMillis() > startTime.getTime()
                    && System.currentTimeMillis() < endTime.getTime()
                    && !ExamState.DISABLED.equals(this.state)) {
                return ExamState.ENABLE;
            }

        }

        return this.state;
    }
}
