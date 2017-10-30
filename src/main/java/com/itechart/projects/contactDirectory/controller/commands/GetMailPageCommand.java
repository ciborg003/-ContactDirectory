package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.stringTemplates.MsgTemplates;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMailPageCommand extends CommandProcess {

    public GetMailPageCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        List<Contact> list = new ArrayList<>();

        try {
            if (request.getParameterValues("contactId") != null) {
                for (String id
                        : request.getParameterValues("contactId")) {
                    Contact c = contactDAO.findEntityById(Integer.parseInt(id));
                    if (c.getEmail() != null && c.getEmail().trim() != "") {
                        list.add(c);
                    }
                }
            }
            List<MsgTemplates> templates = new ArrayList<>();
            for (MsgTemplates template : MsgTemplates.values()) {
                templates.add(template);
            }

            request.setAttribute("templates", templates);
            request.setAttribute("contactList", list);

            request.getRequestDispatcher("mail.jsp").forward(request, response);
        } catch (ServletException | IOException | DAOException ex) {
            LOGGER.error(ex.getMessage());
            try {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        }
    }

}
