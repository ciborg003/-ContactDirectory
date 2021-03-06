package com.itechart.projects.contactDirectory.model.quartz;

import com.itechart.projects.contactDirectory.model.dao.ContactDAO;
import com.itechart.projects.contactDirectory.model.dao.NewContactDAO;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.mail.GoogleMailService;
import com.itechart.projects.contactDirectory.model.mail.Mail;
import com.itechart.projects.contactDirectory.model.mail.MailService;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import com.itechart.projects.contactDirectory.model.stringTemplates.MsgRender;
import com.itechart.projects.contactDirectory.model.stringTemplates.MsgTemplates;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stringtemplate.v4.ST;

public class Birthday implements Job {
    
    private static final Logger LOGGER = Logger.getRootLogger();
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            MsgTemplates template = MsgTemplates.BIRTHDAY_LIST;
            
            NewContactDAO contactDAO = new NewContactDAO(ConnectionManager.getConnection());
            List<Contact> contacts = contactDAO.findContactThatHaveBirthdayToday();
            
            MsgRender render = new MsgRender();
            
            Mail mail = new Mail();
            mail.setMessage(render.getMsgByTemplate(contacts, 
                    MsgTemplates.BIRTHDAY_LIST.getMsg()));
            mail.setTitle("Birthday List");
            Contact reciever = new Contact();
            ResourceBundle bundle = ResourceBundle.getBundle("GMail");
            reciever.setEmail(bundle.getString("email"));
            mail.setContact(reciever);
            MailService mailService = new GoogleMailService();
            mailService.sendMail(mail);
            LOGGER.info("Schedule job has worked...");
        } catch (DAOException ex) {
            LOGGER.error("Error in sending msg by schedule", ex);
        }
    }
}
