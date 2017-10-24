package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.AttachmentDAO;
import com.itechart.projects.contactDirectory.model.dao.PhoneDAO;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            List<Phone> phones = new PhoneDAO().findPhonesByContact(contact);
            request.setAttribute("phoneList", phones);
            List<Attachment> attachments = new AttachmentDAO().
                    findAttachmentsByContact(contact);
            request.setAttribute("attachmentList", attachments);

            request.setAttribute("contact", contact);
            request.setAttribute("genders", EnumGender.values());
            request.setAttribute("familyStates", EnumFamilyState.values());
            request.setAttribute("maxDate", new Date(System.currentTimeMillis()));
            request.setAttribute("action", "saveContactChanges");

            request.getRequestDispatcher("editing.jsp").forward(request, response);
        } catch (ServletException | IOException | DAOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}