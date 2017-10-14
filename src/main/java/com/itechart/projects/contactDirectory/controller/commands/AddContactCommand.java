package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddContactCommand extends CommandProcess {

    public AddContactCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("action", "createContact");
        request.setAttribute("genders", EnumGender.values());
        request.setAttribute("familyStates", EnumFamilyState.values());
        try {
            request.getRequestDispatcher("editing.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
