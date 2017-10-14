package com.itechart.projects.contactDirectory.model.dao;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.Entity;
import com.itechart.projects.contactDirectory.model.entity.EnumPhoneType;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhoneDAO extends AbstractDAO<Integer, Phone> {

    private Connection connection;
    private final String INSERT_PHONE = "{call insertPhone(?,?,?,?,?,?)}";
    private final String FIND_PHONES = "{call findPhonesByIdContact(?)}";
    private final String UPDATE_PHONE = "{call updatePhone(?,?,?,?,?,?,?)}";

    public List<Phone> findPhonesByContact(Contact contact) throws SQLException {
        List<Phone> list = new ArrayList<>();

        CallableStatement statement = connection.prepareCall(FIND_PHONES);
        statement.setInt(1, contact.getId());

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Phone phone = new Phone();
            phone.setId(resultSet.getInt(1));
            phone.setCountryCode(resultSet.getString(2));
            phone.setOperatorCode(resultSet.getString(3));
            phone.setPhoneNumber(resultSet.getString(4));
            phone.setPhoneType(EnumPhoneType.valueOf(resultSet.getString(5)));
            phone.setComment(resultSet.getString(6));
            phone.setIdContact(contact.getId());

            list.add(phone);
        }
        closeStatement(statement);

        return list;
    }

    public void updatePhone(Phone phone) throws SQLException {
        CallableStatement statement = connection.prepareCall(UPDATE_PHONE);

        statement.setInt(1, phone.getId());
        statement.setString(2, phone.getCountryCode());
        statement.setString(3, phone.getOperatorCode());
        statement.setString(4, phone.getPhoneNumber());
        statement.setString(5, phone.getPhoneType().getDescription());
        statement.setInt(6, phone.getIdContact());
        statement.setString(7, phone.getComment());

        statement.executeUpdate();

        closeStatement(statement);
    }

    public Integer createPhone(Phone phone) {
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(INSERT_PHONE);

            statement.setString(1, phone.getCountryCode());
            statement.setString(2, phone.getOperatorCode());
            statement.setString(3, phone.getPhoneNumber());
            statement.setString(4, phone.getPhoneType().getDescription());
            statement.setInt(5, phone.getIdContact());
            statement.setString(6, phone.getComment());

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

    public PhoneDAO() throws SQLException {
        this.connection = ConnectionManager.getConnection();
    }
}
