package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.ContactDAO;
import com.itechart.projects.contactDirectory.model.dao.PhoneDAO;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.entity.EnumPhoneType;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public abstract class CommandProcess {

    protected ContactDAO contactDAO;
    protected String propertyPath;

    public CommandProcess() throws SQLException {
//        this.propertyPath = propertyPath;
        this.contactDAO = new ContactDAO(ConnectionManager.getConnection());
    }

    public abstract void execute(HttpServletRequest request, HttpServletResponse response);

    protected Contact createContact(HttpServletRequest request, HttpServletResponse response) {
        Contact contact = new Contact();

        if ((request.getParameter("contactId") != null)
                && request.getParameter("contactId").trim().length() != 0) {
            contact.setId(Integer.parseInt(request.getParameter("contactId")));
        }
        if (request.getParameter("fName") != null
                && request.getParameter("fName").trim().length() != 0) {
            contact.setName(request.getParameter("fName"));
        }
        if (request.getParameter("lName") != null
                && request.getParameter("lName").trim().length() != 0) {
            contact.setSurname(request.getParameter("lName"));
        }
        if (request.getParameter("patronymic") != null
                && request.getParameter("patronymic").trim().length() != 0) {
            contact.setPatronymic(request.getParameter("patronymic"));
        }
        if (request.getParameter("birthday") != null
                && request.getParameter("birthday").trim().length() != 0) {
            String[] date = request.getParameter("birthday").split("-");
            contact.setDob(new Date(Integer.parseInt(date[0]) - 1900,
                    Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2])));
        }
        if (request.getParameter("gender") != null
                && request.getParameter("gender").trim().length() != 0) {
            contact.setGender(EnumGender.valueOf(request.getParameter("gender")));
        }
        if (request.getParameter("nation") != null
                && request.getParameter("nation").trim().length() != 0) {
            contact.setNationality(request.getParameter("nation"));
        }
        if (request.getParameter("familyState") != null 
                && request.getParameter("familyState").trim().length() != 0) {
            contact.setFamilyState(EnumFamilyState.valueOf(request.getParameter("familyState")));
        }
        if ((request.getParameter("webSite") != null)
                && request.getParameter("webSite").trim().length() != 0) {
            contact.setWebSite(request.getParameter("webSite"));
        }
        if ((request.getParameter("email") != null)
                && request.getParameter("email").trim().length() != 0) {
            contact.setEmail(request.getParameter("email"));
        }
        if ((request.getParameter("job") != null)
                && request.getParameter("job").trim().length() != 0) {
            contact.setJob(request.getParameter("job"));
        }
        if (request.getParameter("country") != null
                && request.getParameter("country").trim().length() != 0) {
            contact.setCountry(request.getParameter("country"));
        }
        if (request.getParameter("city") != null
                && request.getParameter("city").trim().length() != 0) {
            contact.setCity(request.getParameter("city"));
        }
        if (request.getParameter("streetHouseRoom") != null
                && request.getParameter("streetHouseRoom").trim().length() != 0) {
            contact.setStreetHouseRoom(request.getParameter("streetHouseRoom"));
        }
        if (request.getParameter("index") != null
                && request.getParameter("index").trim().length() != 0) {
            contact.setIndexNumber(request.getParameter("index"));
        }

        return contact;
    }

    protected void processPhones(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //1
        String phoneActions[] = request.getParameterValues("phoneAction");
        String phoneIDs[] = request.getParameterValues("phoneID");
        String phoneNumbers[] = request.getParameterValues("phoneNumber");
        String phoneComments[] = request.getParameterValues("phoneComment");
        String phoneTypes[] = request.getParameterValues("phoneType");

        PhoneDAO phoneDAO = new PhoneDAO();

        List<Phone> list = new ArrayList<>();
        //2
        if (phoneIDs == null) {
            return;
        }
        //3
        for (int i = 0; i < phoneIDs.length; i++) {
            //4
            switch (phoneActions[i]) {
                //5
                case "none":
                    break;
                //6
                case "update":
                    Phone phone = new Phone();
                    phone.setId(Integer.parseInt(phoneIDs[i]));
                    phone.setCountryCode(phoneNumbers[i].split("-")[0]);
                    phone.setOperatorCode(phoneNumbers[i].split("-")[1]);
                    phone.setPhoneNumber(phoneNumbers[i].split("-")[2]);
                    phone.setPhoneType(EnumPhoneType.valueOf(phoneTypes[i]));
                    phone.setIdContact(Integer.parseInt(request.getParameter("contactId")));
                    phone.setComment(phoneComments[i]);

                    phoneDAO.updatePhone(phone);
                    break;
                //7
                default:
                    break;
            }
            //8
        }

        //9
        for (int i = phoneIDs.length; i < phoneActions.length; i++) {
            //10
            Phone phone = new Phone();
            phone.setCountryCode(phoneNumbers[i].split("-")[0]);
            phone.setOperatorCode(phoneNumbers[i].split("-")[1]);
            phone.setPhoneNumber(phoneNumbers[i].split("-")[2]);
            phone.setPhoneType(EnumPhoneType.valueOf(phoneTypes[i]));
            phone.setIdContact(Integer.parseInt(request.getParameter("contactId")));
            phone.setComment(phoneComments[i]);

            phoneDAO.createPhone(phone);
        }
        //11
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {

        List<Contact> list = contactDAO.findAll(null, null);

        request.setAttribute("contactList", list);
        request.setAttribute("pageNumber", 1);
        request.setAttribute("recordsOnPage", 10);
        request.setAttribute("recordsCount", contactDAO.getRecordsCount());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void processAttachments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    }
}
