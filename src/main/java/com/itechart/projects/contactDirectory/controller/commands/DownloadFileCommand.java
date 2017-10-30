package com.itechart.projects.contactDirectory.controller.commands;

import com.dropbox.core.DbxException;
import static com.itechart.projects.contactDirectory.controller.commands.CommandProcess.LOGGER;
import com.itechart.projects.contactDirectory.model.dao.AttachmentDAO;
import com.itechart.projects.contactDirectory.model.dropbox.DbxService;
import com.itechart.projects.contactDirectory.model.dropbox.DbxUser;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFileCommand extends CommandProcess {

    private static final String DBX_PROPERTY = "DropBox";

    public DownloadFileCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ResourceBundle bundle = ResourceBundle.getBundle(DBX_PROPERTY);
        DbxUser user = new DbxUser(bundle.getString("token"));
        user.setUsername(bundle.getString("username"));
        DbxService service = null;
        try {
            service = new DbxService(user);
            
            int attachmentID = Integer.parseInt(request.getParameter("attachmentID"));
            Attachment attachment = null;
            
            attachment = new AttachmentDAO().getAttachmentByID(attachmentID);
            ServletOutputStream out = response.getOutputStream();
        
            response.setContentType("application/download;");
            
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(attachment.getFileName(), "UTF-8"));
            response.addHeader("Content-Length", "" + service.getSize(attachment.getUrl()));
            
            if (service.isExists(attachment.getUrl())) {
            service.readFile(attachment.getUrl(), out);
        }
        } catch (DbxException | DAOException | IOException ex) {
            LOGGER.error(ex.getMessage());
            try {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        }

    }

}
