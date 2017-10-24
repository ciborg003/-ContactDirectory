package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class GetMainPageCommand extends CommandProcess {

    public GetMainPageCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        List<Contact> list = null;
        try {
            list = contactDAO.findAll(null, null);

            request.setAttribute("contactList", list);
            request.setAttribute("pageNumber", 1);
            request.setAttribute("recordsOnPage", 10);
            request.setAttribute("recordsCount", contactDAO.getRecordsCount());

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (DAOException | ServletException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
