package com.itechart.projects.contactDirectory.controller.commands;

import com.dropbox.core.DbxException;
import com.itechart.projects.contactDirectory.model.dropbox.DbxService;
import com.itechart.projects.contactDirectory.model.dropbox.DbxUser;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.entity.EnumPhoneType;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class SaveContactChangesCommand extends CommandProcess {

    private static final String DBX_PROPERTY = "DropBox";

    private static ResourceBundle bundle = null;
    private static DbxService service = null;
    private Contact contact = null;
//    -------------------------------------------
    private Map<Phone, String> phones = new HashMap<>();
    private Attachment attachment = null;
    private String photoAction;
    private InputStream photoStream;
    private String attachmentAction = "";
    private Phone phone = null;
    private String phoneAction = "";
    private List<String> fileNames = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    
    private Savepoint savepoint;

    public SaveContactChangesCommand() {
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
                LOGGER.warn("Isn't multipart content");
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
                    formField(item);
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    LOGGER.info(name + ": " + value);
                } else {
                    LOGGER.info("FieldName: " + item.getFieldName());
                    LOGGER.info("stream size: " + item.getSize());
                    if (item.getFieldName().equals("selectPhoto")) {
                        proccessPhoto(item);
                    } else {
                        proccessAttachment(item);
                    }
                }
            }
            connection.commit();
            savepoint = connection.setSavepoint();
            contactDAO.update(contact);
            proccessPhones();

            processRequest(request, response);
        } catch (DAOException | IOException | FileUploadException | SQLException e) {
            LOGGER.error(e.getMessage());
            try {
                if (connection != null){
                    connection.rollback(savepoint);
                }
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException | SQLException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        }
    }

    private void proccessPhoto(FileItem item) throws IOException {
        photoStream = item.getInputStream();
        if (photoAction != null
                && photoAction.equals("change")
                && photoStream != null
                && photoStream.available() > 0) {
            try {
                String ext = item.getName().substring(item.getName().lastIndexOf("."));
                String photoName = contact.getId() + ext;
                String photoPath = bundle.getString("photo") + "/" + photoName;
                if (service.isExists(photoPath)) {
                    service.deleteFile(photoPath);
                    service.uploadFile(photoStream, photoPath);
                } else {
                    service.uploadFile(photoStream, photoPath);
                }
                System.out.println("PhotoPath: " + photoPath);
                contact.setPhotoUrl(photoPath);
                contactDAO.updatePhoto(contact);
            } catch (DAOException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

    private void proccessAttachment(FileItem item) {
        String pathToDbx = null;
        String file = item.getName();
        Path path = Paths.get(file);
        attachment.setFileName(path.getFileName().toString());
        pathToDbx = bundle.getString("attachment") + "/"
                + contact.getId() + "/" + attachment.getFileName();

        try {
            switch (attachmentAction) {
                case "create":
                    attachment.setUrl(pathToDbx);
                    service.uploadFile(item.getInputStream(), pathToDbx);
                    attachmentDAO.createAttachment(attachment);
                    attachment = null;
                    attachmentAction = null;
                    break;
                case "edit":
                    service.deleteFile(attachment.getUrl());
                    attachment.setUrl(pathToDbx);
                    service.uploadFile(item.getInputStream(), pathToDbx);
                    System.out.println("Att: " + attachment);
                    attachmentDAO.updateAttachment(attachment);
                    attachment = null;
                    attachmentAction = null;
                    break;
                case "delete":
                    service.deleteFile(attachment.getUrl());
                    attachmentDAO.deleteAttachment(attachment);
                    attachment = null;
                    attachmentAction = null;
                    break;
            }
        } catch (DAOException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        pathToDbx = null;
    }

    private void formField(FileItem item) throws UnsupportedEncodingException {
        String name = item.getFieldName();
        String value = item.getString("UTF-8");
        switch (name) {
            case "contactId":
                if (value != null && value.trim().length() > 0) {
                    contact.setId(Integer.parseInt(value));
                }
                break;
            case "fName":
                if (value != null && value.trim().length() > 0) {
                    contact.setName(value);
                }
                break;
            case "lName":
                if (value != null && value.trim().length() > 0) {
                    contact.setSurname(value);
                }
                break;
            case "patronymic":
                if (value != null && value.trim().length() > 0) {
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
                if (value != null && value.trim().length() > 0) {
                    contact.setGender(EnumGender.valueOf(value));
                }
                break;
            case "nation":
                if (value != null && value.trim().length() > 0) {
                    contact.setNationality(value);
                }
                break;
            case "familyState":
                if (value != null && value.trim().length() > 0) {
                    contact.setFamilyState(EnumFamilyState.valueOf(value));
                }
                break;
            case "webSite":
                if (value != null && value.trim().length() > 0) {
                    contact.setWebSite(value);
                }
                break;
            case "email":
                if (value != null && value.trim().length() > 0) {
                    contact.setEmail(value);
                }
                break;
            case "job":
                if (value != null && value.trim().length() > 0) {
                    contact.setJob(value);
                }
                break;
            case "country":
                if (value != null && value.trim().length() > 0) {
                    contact.setCountry(value);
                }
                break;
            case "city":
                if (value != null && value.trim().length() > 0) {
                    contact.setCity(value);
                }
                break;
            case "streetHouseRoom":
                if (value != null && value.trim().length() > 0) {
                    contact.setStreetHouseRoom(value);
                }
                break;
            case "index":
                if (value != null && value.trim().length() > 0) {
                    contact.setIndexNumber(value);
                }
                break;
            case "phoneAction":
                phoneAction = value;
                break;
            case "phoneNumber":
                phone = new Phone();
                phone.setIdContact(this.contact.getId());
                phone.setCountryCode(value.split("-")[0]);
                phone.setOperatorCode(value.split("-")[1]);
                phone.setPhoneNumber(value.split("-")[2]);
                break;
            case "phoneComment":
                phone.setComment(value);
                break;
            case "phoneType":
                phone.setPhoneType(EnumPhoneType.valueOf(value));
                break;
            case "phoneID":
                try {
                    phone.setId(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    LOGGER.error("Try parse " + value + " to int:\n" + 
                            e.getMessage());
                }
                phones.put(phone, phoneAction);
                phone = null;
                phoneAction = "";
                break;
            case "attachmentAction":
                attachmentAction = value;
                attachment = new Attachment();
                attachment.setIdContact(contact.getId());
                break;
            case "loadDate":
                attachment.setLoadDate(new Timestamp(System.currentTimeMillis()));
                break;
            case "attachmentComment":
                attachment.setComment(value);
                break;
            case "filePath":
                attachment.setUrl(value);
                break;
            case "idAttachment":
                attachment.setId(Integer.parseInt(value));
                break;
            case "photoAction":
                this.photoAction = value;
                break;
        }
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
