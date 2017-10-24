package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchCommand extends CommandProcess {

    private static Map<String, Contact[]> session = new HashMap();

    public SearchCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Contact arr[] = arr = new Contact[2];
            Contact contactFrom = null;
            Contact contactTo = new Contact();
            
            Integer pageNumber = 1;
            Integer recordsOnPage = 10;
            if (request.getParameter("recordsOnPage") != null && request.getParameter("pageNumber") != null) {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                recordsOnPage = Integer.parseInt(request.getParameter("recordsOnPage"));
            } else {
                contactFrom = createContact(request, response);
                
                if ((request.getParameter("birthdayTo") != null) && !"".equals(request.getParameter("birthdayTo").trim())) {
                    String[] date = request.getParameter("birthday").split("-");
                    contactTo.setDob(new Date(Integer.parseInt(date[0]) - 1900,
                            Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2])));
                }
            }
            
            System.out.println(contactFrom);
            System.out.println(contactTo);
            
            if (contactFrom == null && contactTo.getDob() == null) {
                Contact array[] = session.get(request.getSession().getId());
                contactFrom = array[0];
                contactTo = array[1];
            }
            arr[0] = contactFrom;
            arr[1] = contactTo;
            session.put(request.getSession().getId(), arr);
            
            List<Contact> list = contactDAO.search(contactFrom, contactTo,
                    (pageNumber-1)*recordsOnPage, recordsOnPage);
            Integer count = contactDAO.searchCount(contactFrom, contactTo);
            
            request.setAttribute("action", "search");
            request.setAttribute("contactList", list);
            request.setAttribute("recordsCount", count);
            request.setAttribute("recordsOnPage", recordsOnPage);
            request.setAttribute("pageNumber", pageNumber);
            try {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        } catch (DAOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

}
