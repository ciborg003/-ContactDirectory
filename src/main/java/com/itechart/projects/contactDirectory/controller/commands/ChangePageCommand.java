package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePageCommand extends CommandProcess {

    public ChangePageCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Integer page = Integer.parseInt(request.getParameter("pageNumber"));
        Integer length = Integer.parseInt(request.getParameter("recordsOnPage"));
        List<Contact> list = null;
        try {
            list = contactDAO.findAll((page - 1) * length, length);

            request.setAttribute("pageNumber", page);
            request.setAttribute("recordsOnPage", length);

            request.setAttribute("recordsCount", contactDAO.getRecordsCount());
            request.setAttribute("contactList", list);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (DAOException | ServletException | IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
}
