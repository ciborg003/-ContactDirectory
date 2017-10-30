package com.itechart.projects.contactDirectory.controller.commands;

import com.dropbox.core.DbxException;
import static com.itechart.projects.contactDirectory.controller.commands.CommandProcess.LOGGER;
import com.itechart.projects.contactDirectory.model.dropbox.DbxService;
import com.itechart.projects.contactDirectory.model.dropbox.DbxUser;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPhotoCommand extends CommandProcess {

    private static final String DBX_PROPERTY = "DropBox";

    private ResourceBundle bundle = null;
    private DbxService service = null;

    public GetPhotoCommand() throws SQLException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpg");
        bundle = ResourceBundle.getBundle(DBX_PROPERTY);
        DbxUser user = new DbxUser(bundle.getString("token"));
        user.setUsername(bundle.getString("username"));

        String photo = request.getParameter("photo");
        String defaultPhoto = bundle.getString("default_photo");

        try {
            service = new DbxService(user);
            if (photo != null 
                    && photo.trim().length() > 0 
                    &&  service.isExists(photo)) {
                service.readFile(photo, response.getOutputStream());
            } else {
                service.readFile(defaultPhoto, response.getOutputStream());
            }
        } catch (DbxException | IOException ex) {
            LOGGER.error(ex.getMessage());
            try {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException ex1) {
                LOGGER.error("Can't forward to error page", ex1);
            }
        } finally {
            ConnectionManager.closeConnection(connection);
        }
    }

}
