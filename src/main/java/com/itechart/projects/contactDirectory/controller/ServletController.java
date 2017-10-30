package com.itechart.projects.contactDirectory.controller;

import com.itechart.projects.contactDirectory.controller.commands.CommandProcess;
import com.itechart.projects.contactDirectory.controller.commands.Commands;
import com.itechart.projects.contactDirectory.model.dao.ContactDAO;
import com.itechart.projects.contactDirectory.model.quartz.Birthday;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ServletController extends HttpServlet {

    private ContactDAO contactDAO = null;
    private static final Logger LOGGER = Logger.getRootLogger();

    @Override
    public void init() throws ServletException {
        try {
            super.init();

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail job = JobBuilder.newJob(Birthday.class)
                    .withIdentity("dummyJobName", "group1").build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("dummyTriggerName", "group1")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
                    .build();
            // Планируем задание с помощью JobDetail и Trigger
            scheduler.scheduleJob(job, trigger);

            // Запускаем планировщик
            scheduler.start();
        } catch (SchedulerException ex) {
            LOGGER.error("Error int scheduling the job", ex);
        }
    }

    public ServletController() throws SQLException {
        contactDAO = new ContactDAO();
    }

    protected void proccess(HttpServletRequest request, HttpServletResponse response) {
        CommandProcess command = Commands.getRequestProcessor(request.getParameter("action"));
        if (command != null) {
            command.execute(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        proccess(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        proccess(request, response);
    }
}
