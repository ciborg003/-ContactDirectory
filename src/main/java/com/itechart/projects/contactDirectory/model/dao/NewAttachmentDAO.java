package com.itechart.projects.contactDirectory.model.dao;

import static com.itechart.projects.contactDirectory.model.dao.AbstractDAO.LOGGER;
import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewAttachmentDAO extends AbstractDAO<Integer, Attachment> {

    private Connection connection;
    private final String CREATE_ATTACHMENT
            = "insert into attachment (loadDate, path, fileName, idContact, comment) \n" +
"	values (?, ?, ?, ?, ?); select last_insert_id();";
    private final String FIND_ATTACHMENTS = "select attachment.path, attachment.fileName, attachment.loadDate, attachment.idContact, attachment.comment from attachment\n" +
"    where attachment.idAttachment = ?;";
    private final String UPDATE_ATTACHMENT = "update attachment set attachment.loadDate = ?, attachment.path = ?, attachment.fileName = ?, \n" +
"    attachment.idContact = ?, attachment.comment = ? where attachment.idAttachment = ?;";
    private final String FIND_ATTACHMENT_BY_ID = "select attachment.path, attachment.fileName, attachment.loadDate, attachment.idContact, attachment.comment from attachment\n" +
"    where attachment.idAttachment = ?";
    private final String DELETE_ATTACHMENT = "update attachment set attachment.deleted = now() where attachment.idAttachment = ?";

    public Integer createAttachment(Attachment attachment) throws DAOException {
        PreparedStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(CREATE_ATTACHMENT);

            statement.setTimestamp(1, attachment.getLoadDate());
            statement.setString(2, attachment.getUrl());
            statement.setString(3, attachment.getFileName());
            statement.setInt(4, attachment.getIdContact());
            statement.setString(5, attachment.getComment());

            ResultSet genKey = statement.executeQuery();
            if (genKey.next()) {
                return genKey.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return null;
    }

    public List<Attachment> findAttachmentsByContact(Contact contact) throws DAOException {
        List<Attachment> list = new ArrayList<>();
        PreparedStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ATTACHMENTS);
            statement.setInt(1, contact.getId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Attachment a = new Attachment();
                a.setId(resultSet.getInt(1));
                a.setLoadDate(resultSet.getTimestamp(2));
                a.setUrl(resultSet.getString(3));
                a.setFileName(resultSet.getString(4));
                a.setComment(resultSet.getString(5));
                a.setIdContact(contact.getId());

                list.add(a);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return list;
    }

    public void updateAttachment(Attachment attachment) throws DAOException {
        PreparedStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(UPDATE_ATTACHMENT);
            
            statement.setTimestamp(1, attachment.getLoadDate());
            statement.setString(2, attachment.getUrl());
            statement.setString(3, attachment.getFileName());
            statement.setInt(4, attachment.getIdContact());
            statement.setString(5, attachment.getComment());
            statement.setInt(6, attachment.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public Attachment getAttachmentByID(Integer id) throws DAOException {
        PreparedStatement statement = null;
        Attachment attachment = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ATTACHMENT_BY_ID);

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                attachment = new Attachment();
                attachment.setId(id);
                attachment.setUrl(resultSet.getString(1));
                attachment.setFileName(resultSet.getString(2));
                attachment.setLoadDate(resultSet.getTimestamp(3));
                attachment.setIdContact(resultSet.getInt(4));
                attachment.setComment(resultSet.getString(5));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return attachment;
    }

    public void deleteAttachment(Attachment attachment) throws DAOException {
        PreparedStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(DELETE_ATTACHMENT);

            statement.setInt(1, attachment.getId());
            statement.execute();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public NewAttachmentDAO() {
    }

    public NewAttachmentDAO(Connection connection) {
        LOGGER.info("Connection is " + connection);
        this.connection = connection;
    }

}
