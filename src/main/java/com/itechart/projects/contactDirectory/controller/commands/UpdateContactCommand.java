package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.AttachmentDAO;
import com.itechart.projects.contactDirectory.model.dao.PhoneDAO;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateContactCommand extends CommandProcess {

    public UpdateContactCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer idContact = Integer.parseInt(request.getParameter("contactID"));
            Contact contact = contactDAO.findEntityById(idContact);

            List<Phone> phones = phoneDAO.findPhonesByContact(contact);
            request.setAttribute("phoneList", phones);
            List<Attachment> attachments = attachmentDAO.findAttachmentsByContact(contact);
            request.setAttribute("attachmentList", attachments);

            request.setAttribute("contact", contact);
            request.setAttribute("genders", EnumGender.values());
            request.setAttribute("familyStates", EnumFamilyState.values());
            request.setAttribute("maxDate", new Date(System.currentTimeMillis()));
            request.setAttribute("action", "saveContactChanges");

            request.getRequestDispatcher("editing.jsp").forward(request, response);
        } catch (ServletException | IOException | DAOException ex) {
            LOGGER.error(ex.getMessage());
            try {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        } finally {
            ConnectionManager.closeConnection(connection);
        }
    }
}
