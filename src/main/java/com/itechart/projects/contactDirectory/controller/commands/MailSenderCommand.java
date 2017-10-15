package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.mail.GoogleMailService;
import com.itechart.projects.contactDirectory.model.mail.Mail;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itechart.projects.contactDirectory.model.mail.MailService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;

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
        
        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(MailSenderCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
