package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.ContactDAO;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
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
        } catch (NamingException ex) {
            ex.printStackTrace();
        }

        request.setAttribute("pageNumber", page);
        request.setAttribute("recordsOnPage", length);

        try {
            request.setAttribute("recordsCount", contactDAO.getRecordsCount());
        } catch (NamingException ex) {
            ex.printStackTrace();
        }

        request.setAttribute("contactList", list);
        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
