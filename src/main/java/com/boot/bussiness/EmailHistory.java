package com.boot.bussiness;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.boot.service.SchedulerService;

public class EmailHistory implements Job{

    @Autowired
    private SchedulerService service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
       service.SendEmailAndRemoveEntry();
    }
}