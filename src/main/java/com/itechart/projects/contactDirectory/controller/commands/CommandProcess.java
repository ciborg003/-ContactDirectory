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
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
// logging the error... 
            return;
        }
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Set factory constraints
        // Max constraint - when max is run out, data is written in temp directory
        // set 1MB
        factory.setSizeThreshold(1024 * 1024);
        // set temp dir
        File tempDir = (File) request.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(tempDir);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Set overall request size constraint. Set to 10 MB
        upload.setSizeMax(1024 * 1024 * 10);
        // upload.setFileSizeMax - max file size

        upload.setHeaderEncoding("UTF8");
        try {
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (item.isFormField()) {
                    //Process a regular form field
                    processFormField(item);
                } else {
                    //Process a file upload
                    processUploadedFile(item);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

    private void processUploadedFile(FileItem item) throws Exception {
        String fieldName = item.getFieldName();
        String fileName = item.getName();
        String contentType = item.getContentType();
        System.out.println("----------------------------------------");
        System.out.println("FieldName: " + fieldName);
        System.out.println("FileName: " + fileName);
        System.out.println("ContentType: " + contentType);
        System.out.println("----------------------------------------");
        boolean isInMemory = item.isInMemory();
        long sizeInBytes = item.getSize();
        Path path = Paths.get(fileName);
        System.out.println(path.getFileName());
//        service.uploadFile(item.getInputStream(), "/"+path.getFileName().toString());
//            File uploadedFile = new File("files\\" + path.getFileName());
//            System.out.println(uploadedFile.getAbsolutePath());
//            System.out.println(uploadedFile.getCanonicalPath());
//            item.write(uploadedFile);
//        } else {
//            InputStream uploadedStream = item.getInputStream(); // ...
//            uploadedStream.close();
//    }
    }

    private void processFormField(FileItem item) {
        String name = item.getFieldName();
        String value = item.getString();
    }
}
