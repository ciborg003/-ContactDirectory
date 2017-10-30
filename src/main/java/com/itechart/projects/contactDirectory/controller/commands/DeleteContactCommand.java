package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteContactCommand extends CommandProcess {

    public DeleteContactCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            for (String id : request.getParameterValues("contactId")) {
                contactDAO.delete(Integer.parseInt(id));
            }
            
            processRequest(request, response);
        } catch (DAOException ex) {
            LOGGER.error(ex.getMessage());
            try {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        }
    }
}
