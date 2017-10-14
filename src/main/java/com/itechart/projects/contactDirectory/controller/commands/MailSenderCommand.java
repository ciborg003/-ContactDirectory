package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.mail.GoogleMailService;
import com.itechart.projects.contactDirectory.model.mail.Mail;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itechart.projects.contactDirectory.model.mail.MailService;
import java.util.ArrayList;
import java.util.List;

public class MailSenderCommand extends CommandProcess {

    public MailSenderCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        MailService mailService = new GoogleMailService();

        String content = request.getParameter("mailContent");
        String title = request.getParameter("title");
        List<Contact> list = new ArrayList<>();

        for (String email : request.getParameterValues("email")) {
            Contact c = new Contact();
            c.setEmail(email);
            list.add(c);
        }

        Mail mail = new Mail();
        mail.setContacts(list);
        mail.setMessage(content);
        mail.setTitle(title);
        
        mailService.sendMail(mail);
    }

}
