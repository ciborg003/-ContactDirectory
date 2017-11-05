package com.itechart.projects.contactDirectory.controller.commands;

import com.itechart.projects.contactDirectory.model.dao.NewAttachmentDAO;
import com.itechart.projects.contactDirectory.model.dao.NewContactDAO;
import com.itechart.projects.contactDirectory.model.dao.NewPhoneDAO;
import com.itechart.projects.contactDirectory.model.dao.PhoneDAO;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.entity.EnumPhoneType;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class CommandProcess {

    protected Connection connection;
    protected NewContactDAO contactDAO;
    protected NewPhoneDAO phoneDAO;
    protected NewAttachmentDAO attachmentDAO;
    protected final static Logger LOGGER = Logger.getRootLogger();
    protected ObjectMapper jsonParser = new ObjectMapper();

    private static final String patternWord = "^[A-Za-z]+'?[A-Za-z]+$|^[А-Яа-я]+$";
    private static final String patternPhone = "^\\+?[0-9]{2,3}-[0-9]{2}-[0-9]{7}$";

    public CommandProcess() {
        connection = ConnectionManager.getConnection();
        this.contactDAO = new NewContactDAO(connection);
        this.attachmentDAO = new NewAttachmentDAO(connection);
        this.phoneDAO = new NewPhoneDAO(connection);
    }

    public abstract void execute(HttpServletRequest request, HttpServletResponse response);

    protected Contact createContact(HttpServletRequest request, HttpServletResponse response) {
        Contact contact = new Contact();

        if ((request.getParameter("contactId") != null)
                && request.getParameter("contactId").trim().length() != 0) {
            contact.setId(Integer.parseInt(request.getParameter("contactId")));
        }
        if (request.getParameter("fName") != null
                && request.getParameter("fName").trim().length() > 0) {
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
            try {
                contact.setGender(EnumGender.valueOf(request.getParameter("gender")));
            } catch (Exception e){
            }
        }
        if (request.getParameter("nation") != null
                && request.getParameter("nation").trim().length() != 0) {
            contact.setNationality(request.getParameter("nation"));
        }
        if (request.getParameter("familyState") != null
                && request.getParameter("familyState").trim().length() != 0) {
            try {
            contact.setFamilyState(EnumFamilyState.valueOf(request.getParameter("familyState")));
            } catch (Exception e){
                
            }
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
                && ((String) request.getParameter("city")).trim().length() != 0) {
            contact.setCity(request.getParameter("city"));
        }
        if (request.getParameter("streetHouseRoom") != null
                && ((String) request.getParameter("streetHouseRoom")).trim().length() != 0) {
            contact.setStreetHouseRoom(request.getParameter("streetHouseRoom"));
        }
        if (request.getParameter("index") != null
                && ((String) request.getParameter("index")).trim().length() != 0) {
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
            System.out.println("UPDATE");
            Phone phone = new Phone();
            try {
                switch (phoneActions[i]) {
                    //5
                    case "none":
                        break;
                    //6
                    case "update":
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
                    case "delete":
                        System.out.println("DElete");
                        phone.setId(Integer.parseInt(phoneIDs[i]));
                        phoneDAO.deletePhone(phone);
                        break;
                    default:
                        break;
                }
            } catch (DAOException ex) {
                LOGGER.error(ex.getMessage());
            }
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

            try {
                phoneDAO.createPhone(phone);
            } catch (DAOException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
        //11
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        List<Contact> list = null;
        try {
            list = contactDAO.findAll(null, null);

            request.setAttribute("contactList", list);
            request.setAttribute("pageNumber", 1);
            request.setAttribute("recordsOnPage", 10);
            request.setAttribute("recordsCount", contactDAO.getRecordsCount());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (DAOException | ServletException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }

    }

    protected boolean validateWord(String word) {
        Pattern p = Pattern.compile(patternWord);
        Matcher matcher = p.matcher(word);

        return matcher.find();
    }

    protected boolean validatePhone(String phone) {
        Pattern p = Pattern.compile(patternPhone);
        Matcher matcher = p.matcher(phone);

        return matcher.find();
    }
}
