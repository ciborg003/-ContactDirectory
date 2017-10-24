package com.itechart.projects.contactDirectory.model.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Birthday implements Job{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("BBBBBBBBBBBBB");
        System.out.println("IIIIIIIIIIIII");
        System.out.println("RRRRRRRRRRRRR");
        System.out.println("TTTTTTTTTTTTT");
        System.out.println("HHHHHHHHHHHHH");
        System.out.println("DDDDDDDDDDDDD");
        System.out.println("AAAAAAAAAAAAA");
        System.out.println("YYYYYYYYYYYYY");
    }
}
