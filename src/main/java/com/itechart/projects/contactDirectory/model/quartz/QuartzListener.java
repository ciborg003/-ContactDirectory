package com.itechart.projects.contactDirectory.model.quartz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzListener extends QuartzInitializerListener {
    
    private static final Logger LOGGER = Logger.getRootLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        ServletContext context = sce.getServletContext();

        StdSchedulerFactory factory = (StdSchedulerFactory) context.getAttribute(QUARTZ_FACTORY_KEY);
        try {
            Scheduler scheduler = factory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(Birthday.class).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 15 10 ? * *"))
                    .startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            LOGGER.error("Error in scheduling the job", e);
        }
    }
}
