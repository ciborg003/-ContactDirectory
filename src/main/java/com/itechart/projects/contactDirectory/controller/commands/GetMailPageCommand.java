package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.ContactDAO;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
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
            contactDAO = new ContactDAO(ConnectionManager.getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (request.getParameterValues("contactId") != null) {
            for (String id
                    : request.getParameterValues("contactId")) {
                Contact c = contactDAO.findEntityById(Integer.parseInt(id));
                if (c.getEmail() != null && c.getEmail().trim() != "") {
                    list.add(c);
                }
            }
        }

        request.setAttribute("contactList", list);
        try {
            request.getRequestDispatcher("mail.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }
    }

}
