package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.AttachmentDAO;
import com.itechart.projects.contactDirectory.model.dao.PhoneDAO;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateContactCommand extends CommandProcess {

    public UpdateContactCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Integer idContact = Integer.parseInt(request.getParameter("contactID"));
        Contact contact = contactDAO.findEntityById(idContact);
        try {
            List<Phone> phones = new PhoneDAO().findPhonesByContact(contact);
            request.setAttribute("phoneList", phones);
            List<Attachment> attachments = new AttachmentDAO().
                    findAttachmentsByContact(contact);
            request.setAttribute("attachmentList", attachments);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        request.setAttribute("contact", contact);
        request.setAttribute("genders", EnumGender.values());
        request.setAttribute("familyStates", EnumFamilyState.values());
        request.setAttribute("action", "saveContactChanges");
        try {
            request.getRequestDispatcher("editing.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
