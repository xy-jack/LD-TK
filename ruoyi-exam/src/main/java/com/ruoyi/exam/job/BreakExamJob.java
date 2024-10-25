package com.ruoyi.exam.job;

import com.ruoyi.exam.service.IPaperService;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 超时自动交卷任务
 * @author bool
 */
@Log4j2
@Component
public class BreakExamJob implements Job {

    @Autowired
    private IPaperService paperService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDetail detail = jobExecutionContext.getJobDetail();
        String name = detail.getKey().getName();
        String group = detail.getKey().getGroup();
        String data = String.valueOf(detail.getJobDataMap().get("taskData"));

        log.info("++++++++++定时任务：处理到期的交卷");
        log.info("++++++++++jobName:{}", name);
        log.info("++++++++++jobGroup:{}", group);
        log.info("++++++++++taskData:{}", data);

        // 强制交卷
        paperService.handExam(data);

    }


}
