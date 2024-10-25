package com.ruoyi.exam.domain.dto;

import lombok.Data;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
//@ApiModel(value="考试详情", description="考试详情")
public class ExamDetailRespDTO extends PaperDTO {


    //@ApiModelProperty(value = "单选题列表", required=true)
    private List<PaperQuDTO> radioList;

    //@ApiModelProperty(value = "多选题列表", required=true)
    private List<PaperQuDTO> multiList;

    //@ApiModelProperty(value = "判断题", required=true)
    private List<PaperQuDTO> judgeList;

    //@ApiModelProperty(value = "剩余结束秒数", required=true)
    public Long getLeftSeconds(){

        // 结束时间
        Calendar cl = Calendar.getInstance();
        Date date = Date.from(this.getCreateTime().atZone(ZoneId.systemDefault()).toInstant());
        cl.setTime(date);
        cl.add(Calendar.MINUTE, getTotalTime());

        return (cl.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

}
