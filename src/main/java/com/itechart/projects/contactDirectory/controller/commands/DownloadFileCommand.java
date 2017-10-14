package com.itechart.projects.contactDirectory.controller.commands;

import com.dropbox.core.DbxException;
import com.itechart.projects.contactDirectory.model.dao.AttachmentDAO;
import com.itechart.projects.contactDirectory.model.dropbox.DbxService;
import com.itechart.projects.contactDirectory.model.dropbox.DbxUser;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFileCommand extends CommandProcess{

    public DownloadFileCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        
        DbxUser user = new DbxUser("dT7ZKCx7PaAAAAAAAAAAE0sl7jeJg6OVPMVrN_gxY0-Dwqt4-DGElb1LNxCXAcC8");
        DbxService service = null;
        try {
            service = new DbxService(user);
        } catch (DbxException ex) {
            ex.printStackTrace();
        }
        
        int attachmentID = Integer.parseInt(request.getParameter("attachmentID"));
        Attachment attachment = null;
        
        try {
            attachment = new AttachmentDAO().getAttachmentByID(attachmentID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String path = attachment.getUrl();
        System.out.println("FilePATH: " + path);
        String fileName = Paths.get(path).getFileName().toString();
        System.out.println("FileName: " + fileName);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(service.isExists(path));
        response.setContentType("application/download;");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        service.readFile(path, out);
    }
    
}
