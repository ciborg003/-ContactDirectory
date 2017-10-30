package com.itechart.projects.contactDirectory.controller.commands;

import com.dropbox.core.DbxException;
import static com.itechart.projects.contactDirectory.controller.commands.CommandProcess.LOGGER;
import com.itechart.projects.contactDirectory.model.dao.NewAttachmentDAO;
import com.itechart.projects.contactDirectory.model.dao.NewContactDAO;
import com.itechart.projects.contactDirectory.model.dao.NewPhoneDAO;
import com.itechart.projects.contactDirectory.model.dropbox.DbxService;
import com.itechart.projects.contactDirectory.model.dropbox.DbxUser;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.entity.EnumPhoneType;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class CreateContactCommand extends CommandProcess {
    
    private static final String DBX_PROPERTY = "DropBox";

    private static ResourceBundle bundle = null;
    private static DbxService service = null;
    private Contact contact = null;
//    -------------------------------------------
    private Map<Phone, String> phones = new HashMap<>();
    private Attachment attachment = null;
    private String attachmentAction = "";
    private Map<Attachment, InputStream> attachments = new HashMap<>();

    private Phone phone = null;
    private String phoneAction = "";

    private String photoAction = "";
    private InputStream photoStream = null;
    private String photoExtension = "";

    Map<String, String> map = new HashMap<>();
    
    private Savepoint savepoint;

    public CreateContactCommand() {
        bundle = ResourceBundle.getBundle(DBX_PROPERTY);

        DbxUser user = new DbxUser(bundle.getString("token"));
        user.setUsername(bundle.getString("username"));
        try {
            service = new DbxService(user);
        } catch (DbxException ex) {
            LOGGER.error("Can't create DbxService:\n" + ex.getMessage());
        }

        photoAction = "none";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        contact = new Contact();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
            }
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);
        File tempDir = (File) request.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(tempDir);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 10);

        upload.setHeaderEncoding("UTF-8");
        try {
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    LOGGER.info(name + ": " + value);
                    if (!formField(item)) {
                        LOGGER.warn("Bad value of " + name + ": " + value);
                        try {
                            request.getRequestDispatcher("error.jsp")
                                    .forward(request, response);
                        } catch (ServletException ex) {
                            LOGGER.error("Request dispather error", ex);
                        }
                    }
                } else {
                    LOGGER.info("FieldName: " + item.getFieldName());
                    LOGGER.info("stream size: " + item.getSize());
                    LOGGER.info("FileName: " + item.getName());
                    if (item.getFieldName().equals("selectPhoto")) {
                        if (!photoAction.equals("none")) {
                            photoExtension = item.getName().substring(item.getName().lastIndexOf("."));
                            photoStream = item.getInputStream();
//                        proccessPhoto(item);
                            photoStream = item.getInputStream();
                        }
                    } else {

                        String file = item.getName();
                        Path path = Paths.get(file);
                        attachment.setFileName(path.getFileName().toString());

                        attachments.put(attachment, item.getInputStream());
//                        proccessAttachment(item);
                    }
                }
            }
//            connection = ConnectionManager.getConnection();

            connection.commit();
            savepoint = connection.setSavepoint();
//            contactDAO = new NewContactDAO();
//            phoneDAO = new NewPhoneDAO(connection);
//            attachmentDAO = new NewAttachmentDAO(connection);
            
            contact.setId(contactDAO.create(contact));
            proccessPhoto();
            contactDAO.updatePhoto(contact);
            for (Phone p : phones.keySet()) {
                p.setIdContact(contact.getId());
            }
            proccessPhones();

            for (Attachment a : attachments.keySet()) {

                String pathToDbx = null;
                pathToDbx = bundle.getString("attachment") + "/"
                        + contact.getId() + "/" + a.getFileName();
                a.setUrl(pathToDbx);
                for (int i = 1; service.isExists(pathToDbx); i++) {
                    pathToDbx = bundle.getString("attachment") + "/"
                            + contact.getId() + "/(" + i + ")" + a.getFileName();
                    a.setUrl(pathToDbx);
                    System.out.println(a);
                }
                System.out.println(a);
                a.setIdContact(contact.getId());
                service.uploadFile(attachments.get(a), a.getUrl());
                attachmentDAO.createAttachment(a);
            }

            processRequest(request, response);
        } catch (DAOException | IOException | FileUploadException | SQLException e) {
            LOGGER.error(e.getMessage());
            try {
                connection.rollback(savepoint);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException | SQLException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        }
    }

    private void proccessPhoto() throws IOException, DAOException {
        if (photoAction != null
                && photoAction.equals("change")
                && photoStream != null
                && photoStream.available() > 0) {
            String photoName = contact.getId() + photoExtension;
            String photoPath = bundle.getString("photo") + "/" + photoName;
            service.uploadFile(photoStream, photoPath);
            System.out.println("PhotoPath: " + photoPath);
            contact.setPhotoUrl(photoPath);
            contactDAO.updatePhoto(contact);
        }
    }

    private void proccessAttachment(FileItem item) {
        try {
            String pathToDbx = null;
            String file = item.getName();
            Path path = Paths.get(file);
            attachment.setFileName(path.getFileName().toString());
            pathToDbx = bundle.getString("attachment") + "/"
                    + contact.getId() + "/" + attachment.getFileName();

            attachment.setUrl(pathToDbx);
            attachments.put(attachment, item.getInputStream());
//            service.uploadFile(item.getInputStream(), pathToDbx);
//            attachmentDAO.createAttachment(attachment);
            attachment = null;

        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private boolean formField(FileItem item) throws UnsupportedEncodingException, DAOException {
        String name = item.getFieldName();
        String value = item.getString("UTF-8");
        System.out.println(name + ": " + value);

        switch (name) {
            case "fName":
                if (value != null && validateWord(value)
                        && value.length() < 21) {
                    contact.setName(value);
                } else {
                    return false;
                }
                break;
            case "lName":
                if (value != null && validateWord(value)
                        && value.length() < 21) {
                    contact.setSurname(value);
                } else {
                    return false;
                }
                break;
            case "patronymic":
                if (value != null && validateWord(value)
                        && value.length() < 21) {
                    contact.setPatronymic(value);
                }
                break;
            case "birthday":
                if (value != null && value.trim().length() > 0) {
                    String[] date = value.split("-");
                    contact.setDob(new Date(Integer.parseInt(date[0]) - 1900,
                            Integer.parseInt(date[1]) - 1,
                            Integer.parseInt(date[2]) + 1));
                }
                break;
            case "gender":
                if (value != null && value.trim().length() > 0
                        && !value.toLowerCase().equals("none")) {
                    contact.setGender(EnumGender.valueOf(value));
                }
                break;
            case "nation":
                if (value != null && value.trim().length() > 0
                        && value.length() < 46) {
                    contact.setNationality(value);
                }
                break;
            case "familyState":
                if (value != null && value.trim().length() > 0
                        && !value.toLowerCase().equals("none")) {
                    contact.setFamilyState(EnumFamilyState.valueOf(value));
                }
                break;
            case "webSite":
                if (value != null && value.trim().length() > 0
                        && value.length() < 101) {
                    contact.setWebSite(value);
                }
                break;
            case "email":
                if (value != null && value.trim().length() > 0
                        && value.length() < 46) {
                    contact.setEmail(value);
                }
                break;
            case "job":
                if (value != null && value.trim().length() > 0) {
                    contact.setJob(value);
                }
                break;
            case "country":
                if (value != null && value.trim().length() > 0
                        && value.length() < 21) {
                    contact.setCountry(value);
                }
                break;
            case "city":
                if (value != null && value.trim().length() > 0
                        && value.length() < 21) {
                    contact.setCity(value);
                }
                break;
            case "streetHouseRoom":
                if (value != null && value.trim().length() > 0
                        && value.length() < 46) {
                    contact.setStreetHouseRoom(value);
                }
                break;
            case "index":
                if (value != null && value.trim().length() > 0
                        && value.length() < 46) {
                    contact.setIndexNumber(value);
                }
                break;
            case "phoneAction":
                phoneAction = value;
                break;
            case "phoneNumber":
                phone = new Phone();
                if (validatePhone(value)) {
                    phone.setCountryCode(value.split("-")[0]);
                    phone.setOperatorCode(value.split("-")[1]);
                    phone.setPhoneNumber(value.split("-")[2]);
                } else {
                    phoneAction = "none";
                }
                break;
            case "phoneComment":
                if (value.length() < 256) {
                    phone.setComment(value);
                }
                break;
            case "phoneType":
                phone.setPhoneType(EnumPhoneType.valueOf(value));
                break;
            case "phoneID":
                phones.put(phone, phoneAction);
                phone = null;
                phoneAction = "";
                break;
            case "loadDate":
                attachment = new Attachment();
                attachment.setLoadDate(new Timestamp(System.currentTimeMillis()));
                break;
            case "attachmentComment":
                if (value.length() < 256) {
                    attachment.setComment(value);
                }
                break;
            case "photoAction":
                this.photoAction = value;
                break;
        }

        return true;
    }

    private void proccessPhones() {
        for (Phone p : phones.keySet()) {
            try {
                switch (phones.get(p)) {
                    case "none":
                        break;
                    case "create":
                        phoneDAO.createPhone(p);
                        break;
                    case "update":
                        phoneDAO.updatePhone(p);
                        break;
                    case "delete":
                        phoneDAO.deletePhone(p);
                        break;
                }
            } catch (DAOException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

}
