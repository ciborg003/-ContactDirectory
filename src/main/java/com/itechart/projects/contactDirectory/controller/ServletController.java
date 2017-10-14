package com.itechart.projects.contactDirectory.controller;

import com.itechart.projects.contactDirectory.controller.commands.CommandProcess;
import com.itechart.projects.contactDirectory.controller.commands.Commands;
import com.itechart.projects.contactDirectory.model.dao.ContactDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletController extends HttpServlet {

    private ContactDAO contactDAO = null;

    public ServletController() throws SQLException {
        contactDAO = new ContactDAO();
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) {
        CommandProcess command = Commands.getRequestProcessor(request.getParameter("action"));
        if (command != null) {
            command.execute(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        process(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }
}
