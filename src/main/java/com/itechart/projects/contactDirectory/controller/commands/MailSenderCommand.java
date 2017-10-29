package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.mail.GoogleMailService;
import com.itechart.projects.contactDirectory.model.mail.Mail;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itechart.projects.contactDirectory.model.mail.MailService;
import com.itechart.projects.contactDirectory.model.stringTemplates.MsgRender;
import java.util.ArrayList;
import java.util.List;

public class MailSenderCommand extends CommandProcess {

    public MailSenderCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        MailService mailService = new GoogleMailService();

        String template = request.getParameter("msgTemplate");
        String content = request.getParameter("mailContent");
        String title = request.getParameter("title");
        List<Contact> list = new ArrayList<>();

        try {
            if (!template.equals("None")) {
                for (String email : request.getParameterValues("email")) {
                    Contact contact = contactDAO.findContactByEmail(email);
                    if (contact.getEmail() == null) {
                        contact.setEmail(email);
                        contact.setName("User");
                    }

                    MsgRender render = new MsgRender();
                    Mail mail = new Mail();
                    mail.setContact(contact);
                    mail.setMessage(render.getMsgByTemplate(contact, content));

                    mailService.sendMail(mail);
                }
            } else {
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
            processRequest(request, response);
        } catch (DAOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

}
