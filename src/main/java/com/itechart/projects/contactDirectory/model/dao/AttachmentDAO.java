package com.itechart.projects.contactDirectory.model.dao;

import com.itechart.projects.contactDirectory.model.entity.Attachment;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.Entity;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttachmentDAO extends AbstractDAO<Integer, Attachment> {

    private Connection connection;
    private final String CREATE_ATTACHMENT
            = "{call insertAttachmentinsertAttachment(?,?,?,?)}";
    private final String FIND_ATTACHMENTS = "{call findAttachmentsByIdContact(?)}";
    private final String UPDATE_ATTACHMENT = "{call updateAttachment(?,?,?,?,?)}";
    private final String FIND_ATTACHMENT_BY_ID = "{call findAttachmentByID(?)}";

    public Integer createAttachment(Attachment attachment) {
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(CREATE_ATTACHMENT);

            statement.setDate(1, attachment.getLoadDate());
            statement.setString(2, attachment.getUrl());
            statement.setString(3, attachment.getFileName());
            statement.setInt(4, attachment.getIdContact());
            statement.setString(5, attachment.getComment());

            ResultSet genKey = statement.executeQuery();
            if (genKey.next()) {
                return genKey.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return null;
    }

    public List<Attachment> findAttachmentsByContact(Contact contact) throws SQLException {
        List<Attachment> list = new ArrayList<>();

        CallableStatement statement = connection.prepareCall(FIND_ATTACHMENTS);
        statement.setInt(1, contact.getId());

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Attachment a = new Attachment();
            a.setId(resultSet.getInt(1));
            a.setLoadDate(resultSet.getDate(2));
            a.setUrl(resultSet.getString(3));
            a.setFileName(resultSet.getString(4));
            a.setComment(resultSet.getString(5));
            a.setIdContact(contact.getId());

            list.add(a);
        }

        return list;
    }

    public void updateAttachment(Attachment attachment) throws SQLException {
        CallableStatement statement = connection.prepareCall(UPDATE_ATTACHMENT);

        statement.setInt(1, attachment.getId());
        statement.setDate(2, attachment.getLoadDate());
        statement.setString(3, attachment.getUrl());
        statement.setString(4, attachment.getFileName());
        statement.setInt(5, attachment.getIdContact());
        statement.setString(6, attachment.getComment());
    }
    
    public Attachment getAttachmentByID(Integer id){
        CallableStatement statement = null;
        Attachment attachment = null;
        try {
            statement = connection.prepareCall(FIND_ATTACHMENT_BY_ID);
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                attachment = new Attachment();
                attachment.setId(id);
                attachment.setUrl(resultSet.getString(1));
                attachment.setFileName(resultSet.getString(2));
                attachment.setLoadDate(resultSet.getDate(3));
                attachment.setIdContact(resultSet.getInt(4));
                attachment.setComment(resultSet.getString(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return attachment;
    }

    public AttachmentDAO() throws SQLException {
        connection = ConnectionManager.getConnection();
    }

}
