package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetSearchPageCommand extends CommandProcess{

    public GetSearchPageCommand() throws SQLException {
    }
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        List<String> genders = new ArrayList<>();
        List<String> familyStates = new ArrayList<>();
        for(int i = 0; i < EnumGender.values().length; i++){
            genders.add(EnumGender.values()[i].getDescription());
        }
        for(int i = 0; i < EnumFamilyState.values().length; i++){
            familyStates.add(EnumFamilyState.values()[i].getDescription());
        }
        
        request.setAttribute("genders",EnumGender.values());
        request.setAttribute("familyStates", EnumFamilyState.values());
        System.out.println("Genders: " + EnumGender.values().length);
        System.out.println("FamilySates: " + EnumFamilyState.values().length);
        try {
            request.getRequestDispatcher("search.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
