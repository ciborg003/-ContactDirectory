package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateContactCommand extends CommandProcess {

    public CreateContactCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Contact contact = createContact(request, response);
        try {
            request.setAttribute("contactId", contactDAO.create(contact));
            processPhones(request, response);
            processAttachments(request, response);
            processRequest(request, response);
        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
