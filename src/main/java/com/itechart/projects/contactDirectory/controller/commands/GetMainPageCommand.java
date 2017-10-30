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
            Integer begin = 1, length = 10;
            if (request.getParameter("pageNumber") != null) {
                begin = Integer.parseInt(request.getParameter("pageNumber"));
            }
            if (request.getParameter("recordsOnPage") != null) {
                length = Integer.parseInt(request.getParameter("recordsOnPage"));
            }
            connection.commit();
            list = contactDAO.findAll(begin - 1, length);
            LOGGER.info("liat: ------------" + list.size());
            request.setAttribute("contactList", list);
            request.setAttribute("pageNumber", begin);
            request.setAttribute("recordsOnPage", length);
            request.setAttribute("recordsCount", contactDAO.getRecordsCount());

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (DAOException | ServletException | IOException | SQLException ex) {
            LOGGER.error(ex.getMessage());
            try {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        }
    }
}
