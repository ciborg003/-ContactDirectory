package com.itechart.projects.contactDirectory.model.dao;

import static com.itechart.projects.contactDirectory.model.dao.AbstractDAO.LOGGER;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumPhoneType;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewPhoneDAO extends AbstractDAO<Integer, Phone> {

    private Connection connection;
    private final String INSERT_PHONE = "insert into phone (countryCode, operatorCode, phonenumber, phoneType, idContact, comment) \n" +
"        values (?, ?, ?, ?, ?, ?)";
    private final String FIND_PHONES = "select phone.idPhone, phone.countryCode, phone.operatorCode, phone.phoneNumber, phone.phoneType, phone.comment from phone\n" +
"    where phone.idContact = ? and phone.deleted is null";
    private final String UPDATE_PHONE = "update phone set phone.countryCode = ?, phone.operatorCode = ?, phone.phoneNumber = ?,\n" +
"			phone.phoneType = ?, phone.idContact = ?, phone.comment = ? where phone.idPhone = ?";
    private final String DELETE_PHONE = "update phone set phone.deleted = now() where phone.idPhone = ?";

    public List<Phone> findPhonesByContact(Contact contact) throws DAOException {
        List<Phone> list = new ArrayList<>();
        PreparedStatement statement = null;

//        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_PHONES);
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

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            LOGGER.info("Query: " + statement);
            closeStatement(statement);
//            ConnectionManager.closeConnection(connection);
        }

        return list;
    }

    public void updatePhone(Phone phone) throws DAOException {
        PreparedStatement statement = null;

//        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(UPDATE_PHONE);
            
            statement.setString(1, phone.getCountryCode());
            statement.setString(2, phone.getOperatorCode());
            statement.setString(3, phone.getPhoneNumber());
            statement.setString(4, phone.getPhoneType().getDescription());
            statement.setInt(5, phone.getIdContact());
            statement.setString(6, phone.getComment());
            statement.setInt(7, phone.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.info("Query: " + statement);
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
//            ConnectionManager.closeConnection(connection);
        }
    }

    public Integer createPhone(Phone phone) throws DAOException {
        PreparedStatement statement = null;
//        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(INSERT_PHONE, 
                    PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, phone.getCountryCode());
            statement.setString(2, phone.getOperatorCode());
            statement.setString(3, phone.getPhoneNumber());
            statement.setString(4, phone.getPhoneType().getDescription());
            statement.setInt(5, phone.getIdContact());
            statement.setString(6, phone.getComment());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DAOException("Contact connot be inserted into DB");
            }

            ResultSet genKey = statement.getGeneratedKeys();
            if (genKey.next()) {
                return genKey.getInt(1);
            }
            
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            LOGGER.info("Query: " + statement);
            closeStatement(statement);
//            ConnectionManager.closeConnection(connection);
        }

        return null;
    }

    public void deletePhone(Phone phone) throws DAOException {
        CallableStatement statement = null;

//        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(DELETE_PHONE);

            statement.setInt(1, phone.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            LOGGER.info("Query: " + statement);
            closeStatement(statement);
//            ConnectionManager.closeConnection(connection);
        }
    }

    public NewPhoneDAO() {
    }
    
    public NewPhoneDAO(Connection connection) {
        LOGGER.info("Connection is " + connection);
        this.connection = connection;
    }
}
