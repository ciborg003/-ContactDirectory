package com.itechart.projects.contactDirectory.model.dao;

import static com.itechart.projects.contactDirectory.model.dao.AbstractDAO.LOGGER;
import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NewContactDAO extends AbstractDAO<Integer, Contact> {

    private Connection connection;

    private final String SQL_GET_CONTACT_THAT_BITHDAY_TODAY = "select contact.idContact, contact.name, contact.surname\n"
            + "from contact\n"
            + " where contact.deleted is null\n"
            + " and DAYOFMONTH(contact.dob) = DAYOFMONTH(CURDATE()) and MONTH(contact.dob) = MONTH(CURDATE())";
    
    private final String SQL_UPDATE_CONTACT_PHOTO = "UPDATE contact set contact.photoUrl = ? where contact.idContact = ?";
    private final String SQL_FIND_ALL_CONTACTS = "";
    private final String SQL_RECORDS_COUNT = "SELECT COUNT(*) FROM contact WHERE contact.deleted IS NULL";
    private final String SQL_INSERT_CONTACT = "";
    private final String SQL_UPDATE_CONTACT = "";
    private final String SQL_DELETE_CONTACT = "";
    private final String SQL_FIND_BY_ID = "";
    private final String SQL_FIND_BY_EMAIL = "";

    public List<Contact> findAll(Integer begin, Integer length) throws DAOException {
        List<Contact> contacts = new ArrayList<>();
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(SQL_FIND_ALL_CONTACTS);
            if (begin != null) {
                statement.setInt(1, begin);
            } else {
                statement.setInt(1, 0);
            }
            if (length != null) {
                statement.setInt(2, length);
            } else {
                statement.setInt(2, 10);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setSurname(resultSet.getString(3));
                contact.setPatronymic(resultSet.getString(4));
                contact.setDob(resultSet.getDate(5));
                contact.setGender(EnumGender.valueOf(resultSet.getString(6).toUpperCase()));
                contact.setNationality(resultSet.getString(7));
                contact.setFamilyState(EnumFamilyState.valueOf(resultSet.getString(8).toUpperCase()));
                contact.setJob(resultSet.getString(9));
                contact.setCountry(resultSet.getString(10));
                contact.setCity(resultSet.getString(11));
                contact.setStreetHouseRoom(resultSet.getString(12));
                contact.setIndexNumber(resultSet.getString(13));
                contact.setEmail(resultSet.getString(14));
                contact.setWebSite(resultSet.getString(15));
                contact.setPhotoUrl(resultSet.getString(16));

                contacts.add(contact);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return contacts;
    }

    public int getRecordsCount() throws DAOException {
        Statement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_RECORDS_COUNT);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get records  count:\nConnection: " + connection
                    + "\nStatement: " + statement, e);

            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return 0;
    }

    public Contact findEntityById(Integer id) throws DAOException {
        CallableStatement statement = null;
        Contact contact = new Contact();

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(SQL_FIND_BY_ID);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setSurname(resultSet.getString(3));
                contact.setPatronymic(resultSet.getString(4));
                contact.setDob(resultSet.getDate(5));
                contact.setGender(EnumGender.valueOf(resultSet.getString(6).toUpperCase()));
                contact.setNationality(resultSet.getString(7));
                contact.setFamilyState(EnumFamilyState.valueOf(resultSet.getString(8).toUpperCase()));
                contact.setJob(resultSet.getString(9));
                contact.setCountry(resultSet.getString(10));
                contact.setCity(resultSet.getString(11));
                contact.setStreetHouseRoom(resultSet.getString(12));
                contact.setIndexNumber(resultSet.getString(13));
                contact.setEmail(resultSet.getString(14));
                contact.setWebSite(resultSet.getString(15));
                contact.setPhotoUrl(resultSet.getString(16));
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find contact by ID:\nConnection: " + connection
                    + "\nStatement: " + statement, e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return contact;
    }

    public List<Contact> search(Contact from, Contact to, Integer begin, Integer length) throws DAOException {
        List<Contact> contacts = new ArrayList<>();
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall("{call contactSearching(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            statement.setString(1, from.getName());
            statement.setString(2, from.getSurname());
            statement.setString(3, from.getPatronymic());
            statement.setDate(4, from.getDob());
            statement.setDate(5, to.getDob());
            if (from.getGender() == null) {
                statement.setString(6, null);
            } else {
                statement.setString(6, from.getGender().getDescription());
            }
            statement.setString(7, from.getNationality());
            if (from.getFamilyState() == null) {
                statement.setString(8, null);
            } else {
                statement.setString(8, from.getFamilyState().getDescription());
            }
            statement.setString(9, from.getCountry());
            statement.setString(10, from.getCity());
            statement.setString(11, from.getStreetHouseRoom());
            statement.setString(12, from.getIndexNumber());
            statement.setInt(13, begin);
            statement.setInt(14, length);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setSurname(resultSet.getString(3));
                contact.setPatronymic(resultSet.getString(4));
                contact.setDob(resultSet.getDate(5));
                contact.setGender(EnumGender.valueOf(resultSet.getString(6).toUpperCase()));
                contact.setNationality(resultSet.getString(7));
                contact.setFamilyState(EnumFamilyState.valueOf(resultSet.getString(8).toUpperCase()));
                contact.setJob(resultSet.getString(9));
                contact.setCountry(resultSet.getString(10));
                contact.setCity(resultSet.getString(11));
                contact.setStreetHouseRoom(resultSet.getString(12));
                contact.setIndexNumber(resultSet.getString(13));
                contact.setEmail(resultSet.getString(14));
                contact.setWebSite(resultSet.getString(15));

                contacts.add(contact);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't search contacts:\nConnection: " + connection
                    + "\nStatement: " + statement, e);

            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return contacts;
    }

    public int searchCount(Contact from, Contact to) throws DAOException {
        List<Contact> contacts = new ArrayList<>();
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall("{call countContactSearching(?,?,?,?,?,?,?,?,?,?,?,?)}");
            statement.setString(1, from.getName());
            statement.setString(2, from.getSurname());
            statement.setString(3, from.getPatronymic());
            statement.setDate(4, from.getDob());
            statement.setDate(5, to.getDob());
            if (from.getGender() == null) {
                statement.setString(6, null);
            } else {
                statement.setString(6, from.getGender().getDescription());
            }
            statement.setString(7, from.getNationality());
            if (from.getFamilyState() == null) {
                statement.setString(8, null);
            } else {
                statement.setString(8, from.getFamilyState().getDescription());
            }
            statement.setString(9, from.getCountry());
            statement.setString(10, from.getCity());
            statement.setString(11, from.getStreetHouseRoom());
            statement.setString(12, from.getIndexNumber());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get records  count in search:\nConnection: " + connection
                    + "\nStatement: " + statement, e);
            LOGGER.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return 0;
    }

    public void delete(Integer id) throws DAOException {
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(SQL_DELETE_CONTACT);
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException ex) {
            LOGGER.error("Can't delete contact:\nConnection: " + connection
                    + "\nStatement: " + statement, ex);
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public Integer create(Contact entity) throws DAOException {
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(SQL_INSERT_CONTACT);

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getPatronymic());
            statement.setDate(4, entity.getDob());
            statement.setString(5, entity.getGender().getDescription());
            statement.setString(6, entity.getNationality());
            statement.setString(7, entity.getFamilyState().getDescription());
            statement.setString(8, entity.getJob());
            statement.setString(9, entity.getCountry());
            statement.setString(10, entity.getCity());
            statement.setString(11, entity.getStreetHouseRoom());
            statement.setString(12, entity.getIndexNumber());
            statement.setString(13, entity.getEmail());
            statement.setString(14, entity.getWebSite());
            statement.setString(15, entity.getPhotoUrl());
            ResultSet genKey = statement.executeQuery();
            if (genKey.next()) {
                return genKey.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't insert contact:\nConnection: " + connection
                    + "\nStatement: " + statement, e);
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return null;
    }

    public void update(Contact entity) throws DAOException {
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(SQL_UPDATE_CONTACT);

            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getPatronymic());
            statement.setDate(5, entity.getDob());
            statement.setString(6, entity.getGender().getDescription());
            statement.setString(7, entity.getNationality());
            statement.setString(8, entity.getFamilyState().getDescription());
            statement.setString(9, entity.getJob());
            statement.setString(10, entity.getCountry());
            statement.setString(11, entity.getCity());
            statement.setString(12, entity.getStreetHouseRoom());
            statement.setString(13, entity.getIndexNumber());
            statement.setString(14, entity.getEmail());
            statement.setString(15, entity.getWebSite());

            statement.execute();
        } catch (SQLException ex) {
            LOGGER.error("Can't update contact:\nConnection: " + connection
                    + "\nStatement: " + statement, ex);
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public void updatePhoto(Contact contact) throws DAOException {
        PreparedStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareStatement(SQL_UPDATE_CONTACT_PHOTO);

            statement.setString(1, contact.getPhotoUrl());
            statement.setInt(2, contact.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Can't update photo:\nConnection: " + connection
                    + "\nStatement: " + statement, ex);
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }
    }

    public Contact findContactByEmail(String email) throws DAOException {
        Contact contact = new Contact();
        CallableStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(SQL_FIND_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setSurname(resultSet.getString(3));
                contact.setPatronymic(resultSet.getString(4));
                contact.setDob(resultSet.getDate(5));
                contact.setGender(EnumGender.valueOf(resultSet.getString(6).toUpperCase()));
                contact.setNationality(resultSet.getString(7));
                contact.setFamilyState(EnumFamilyState.valueOf(resultSet.getString(8).toUpperCase()));
                contact.setJob(resultSet.getString(9));
                contact.setCountry(resultSet.getString(10));
                contact.setCity(resultSet.getString(11));
                contact.setStreetHouseRoom(resultSet.getString(12));
                contact.setIndexNumber(resultSet.getString(13));
                contact.setEmail(resultSet.getString(14));
                contact.setWebSite(resultSet.getString(15));
                contact.setPhotoUrl(resultSet.getString(16));
            }
        } catch (SQLException ex) {
            LOGGER.error("Can't find photo by ID:\nConnection: " + connection
                    + "\nStatement: " + statement, ex);
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return contact;
    }

    public List<Contact> findContactThatHaveBirthdayToday() throws DAOException {
        List<Contact> contacts = new ArrayList<>();
        PreparedStatement statement = null;

        connection = ConnectionManager.getConnection();

        try {
            statement = connection.prepareCall(SQL_GET_CONTACT_THAT_BITHDAY_TODAY);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setSurname(resultSet.getString(3));

                contacts.add(contact);
            }
        } catch (SQLException ex) {
            LOGGER.error("Can't find contacts that have birthday today:"
                    + "\nConnection: " + connection
                    + "\nStatement: " + statement, ex);
            throw new DAOException(ex);
        } finally {
            closeStatement(statement);
            ConnectionManager.closeConnection(connection);
        }

        return contacts;
    }

    public NewContactDAO(Connection connection) {
        LOGGER.info("Connection is " + connection);
        this.connection = connection;
    }

    public NewContactDAO() {
    }
}
