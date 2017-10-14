package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.ContactDAO;
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

public class DeleteContactCommand extends CommandProcess{

    public DeleteContactCommand() throws SQLException {
    }
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            contactDAO = new ContactDAO(ConnectionManager.getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        for (String id: request.getParameterValues("contactId")){
            try {
                contactDAO.delete(Integer.parseInt(id));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try {
            processRequest(request, response);
        } catch (ServletException | IOException | NamingException ex) {
            ex.printStackTrace();
        }
    }
}