package com.itechart.projects.contactDirectory.model.dao;

import static com.itechart.projects.contactDirectory.model.dao.AbstractDAO.LOGGER;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumPhoneType;
import com.itechart.projects.contactDirectory.model.entity.Phone;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO extends AbstractDAO<Integer, Phone> {

    private Connection connection;
    private final String INSERT_PHONE = "{call insertPhone(?,?,?,?,?,?)}";
    private final String FIND_PHONES = "{call findPhonesByIdContact(?)}";
    private final String UPDATE_PHONE = "{call updatePhone(?,?,?,?,?,?,?)}";
    private final String DELETE_PHONE = "{call deletePhone(?)}";

    public PhoneDAO(Connection connection) {
        LOGGER.info("Connection is " + connection);
        this.connection = connection;
    }

    public List<Phone> findPhonesByContact(Contact contact) throws DAOException {
        List<Phone> list = new ArrayList<>();
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(FIND_PHONES);
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
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return list;
    }

    public void updatePhone(Phone phone) throws DAOException {
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(UPDATE_PHONE);

            statement.setInt(1, phone.getId());
            statement.setString(2, phone.getCountryCode());
            statement.setString(3, phone.getOperatorCode());
            statement.setString(4, phone.getPhoneNumber());
            statement.setString(5, phone.getPhoneType().getDescription());
            statement.setInt(6, phone.getIdContact());
            statement.setString(7, phone.getComment());

            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public Integer createPhone(Phone phone) throws DAOException {
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

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
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return null;
    }

    public void deletePhone(Phone phone) throws DAOException {
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(DELETE_PHONE);

            statement.setInt(1, phone.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public PhoneDAO() {
    }

}
