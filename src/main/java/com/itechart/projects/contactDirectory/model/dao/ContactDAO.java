package com.itechart.projects.contactDirectory.model.dao;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.entity.EnumFamilyState;
import com.itechart.projects.contactDirectory.model.entity.EnumGender;
import com.itechart.projects.contactDirectory.model.pool.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class ContactDAO extends AbstractDAO<Integer, Contact> {

    private Connection connection;

    private final String SQL_FIND_ALL_CONTACTS = "{call findAllContacts(?, ?)}";
    private final String SQL_RECORDS_COUNT = "Select count(*) from contact";
    private final String SQL_INSERT_CONTACT
            = "{call insertContact(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private final String SQL_UPDATE_CONTACT
            = "{call updateContact(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private final String SQL_DELETE_CONTACT = "{call deleteContact(?)}";
    private final String SQL_FIND_BY_ID = "{call findContactByID(?)}";
    private final String SQL_SEARCH = "select contact.idContact, contact.name, contact.surname, contact.patronymic,contact.dob, contact.gender, nationality.nation, \n"
            + "    contact.familyState, job.job, country.country, city.city, address.streetHouseRoom, indexes.indexName, contact.email, contact.webSite\n"
            + "	from contact\n"
            + " left join job on job.idJob = contact.idJob "
            + "	left join address on address.idAddress = contact.idAddress"
            + "	left join country on country.idCountry = address.idCountry "
            + "	left join city on city.idCity = address.idCity"
            + "    left join indexes on indexes.idIndex = address.idIndex"
            + "	left join nationality on nationality.idNationality = contact.idNationality \n"
            + "    where contact.deleted is null and \n"
            + "    (? is null or  upper(contact.name) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(contact.surname) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(contact.patronymic) like concat_ws('','%', upper(?),'%')) and \n"
            + "    (? is null or contact.dob >= ?) and \n"
            + "    (? is null or contact.dob <= ?) and \n"
            + "    (? is null or contact.gender = ?) and \n"
            + "    (? is null or upper(nationality.nation) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or contact.familyState = ?) and \n"
            + "    (? is null or upper(country.country) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(city.city) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(address.streetHouseRoom) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(indexes.indexName) like concat_ws('','%',upper(?),'%')) \n"
            + "    limit ?, ?;";
    private final String SQL_SEARCH_COUNT = "select count(*) \n"
            + "	from contact\n"
            + " left join job on job.idJob = contact.idJob "
            + "	left join address on address.idAddress = contact.idAddress"
            + "	left join country on country.idCountry = address.idCountry "
            + "	left join city on city.idCity = address.idCity"
            + "    left join indexes on indexes.idIndex = address.idIndex"
            + "	left join nationality on nationality.idNationality = contact.idNationality \n"
            + "    where contact.deleted is null and \n"
            + "    (? is null or  upper(contact.name) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(contact.surname) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(contact.patronymic) like concat_ws('','%', upper(?),'%')) and \n"
            + "    (? is null or contact.dob >= ?) and \n"
            + "    (? is null or contact.dob <= ?) and \n"
            + "    (? is null or contact.gender = ?) and \n"
            + "    (? is null or upper(nationality.nation) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or contact.familyState = ?) and \n"
            + "    (? is null or upper(country.country) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(city.city) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(address.streetHouseRoom) like concat_ws('','%',upper(?),'%')) and \n"
            + "    (? is null or upper(indexes.indexName) like concat_ws('','%',upper(?),'%'));";

    public List<Contact> findAll(Integer begin, Integer length) throws NamingException {
        List<Contact> contacts = new ArrayList<>();
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = ConnectionManager.getConnection();
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

                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return contacts;
    }

    public int getRecordsCount() throws NamingException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_RECORDS_COUNT);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return 0;
    }

    public Contact findEntityById(Integer id) {
        CallableStatement statement = null;
        Contact contact = new Contact();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return contact;
    }

    public List<Contact> search(Contact from, Contact to, Integer begin, Integer length) {
        List<Contact> contacts = new ArrayList<>();
        Connection connection = null;
        CallableStatement statement = null;
//        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareCall("{call contactSearching(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            statement.setString(1, from.getName());
            statement.setString(2, from.getSurname());
            statement.setString(3, from.getPatronymic());
            statement.setDate(4, from.getDob());
            statement.setDate(5, to.getDob());
            statement.setString(6, from.getGender().getDescription());
            statement.setString(7, from.getNationality());
            statement.setString(8, from.getFamilyState().getDescription());
            statement.setString(9, from.getCountry());
            statement.setString(10, from.getCity());
            statement.setString(11, from.getStreetHouseRoom());
            statement.setString(12, from.getIndexNumber());
            statement.setInt(13, begin);
            statement.setInt(14, length);
//            statement = connection.prepareStatement(SQL_SEARCH);
//            statement.setString(1, from.getName());
//            statement.setString(2, from.getName());
//            statement.setString(3, from.getSurname());
//            statement.setString(4, from.getSurname());
//            statement.setString(5, from.getPatronymic());
//            statement.setString(6, from.getPatronymic());
//            statement.setDate(7, from.getDob());
//            statement.setDate(8, from.getDob());
//            statement.setDate(9, to.getDob());
//            statement.setDate(10, to.getDob());
//            statement.setString(11, from.getGender() + "");
//            statement.setString(12, from.getGender() + "");
//            statement.setString(13, from.getNationality());
//            statement.setString(14, from.getNationality());
//            statement.setString(15, from.getFamilyState() + "");
//            statement.setString(16, from.getFamilyState() + "");
//            statement.setString(17, from.getCountry());
//            statement.setString(18, from.getCountry());
//            statement.setString(19, from.getCity());
//            statement.setString(20, from.getCity());
//            statement.setString(21, from.getStreetHouseRoom());
//            statement.setString(22, from.getStreetHouseRoom());
//            statement.setString(23, from.getIndexNumber());
//            statement.setString(24, from.getIndexNumber());
//            if (begin != null) {
//                statement.setInt(25, begin);
//            } else {
//                statement.setInt(25, 0);
//            }
//            if (length != null) {
//                statement.setInt(26, length);
//            } else {
//                statement.setInt(26, 10);
//            }

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
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return contacts;
    }

    public int searchCount(Contact from, Contact to) {
        List<Contact> contacts = new ArrayList<>();
        Connection connection = null;
        CallableStatement statement = null;
//        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareCall("{call countContactSearching(?,?,?,?,?,?,?,?,?,?,?,?)}");
            statement.setString(1, from.getName());
            statement.setString(2, from.getSurname());
            statement.setString(3, from.getPatronymic());
            statement.setDate(4, from.getDob());
            statement.setDate(5, to.getDob());
            statement.setString(6, from.getGender().getDescription());
            statement.setString(7, from.getNationality());
            statement.setString(8, from.getFamilyState().getDescription());
            statement.setString(9, from.getCountry());
            statement.setString(10, from.getCity());
            statement.setString(11, from.getStreetHouseRoom());
            statement.setString(12, from.getIndexNumber());
//            statement = connection.prepareStatement(SQL_SEARCH_COUNT);
//            statement.setString(1, from.getName());
//            statement.setString(2, from.getName());
//            statement.setString(3, from.getSurname());
//            statement.setString(4, from.getSurname());
//            statement.setString(5, from.getPatronymic());
//            statement.setString(6, from.getPatronymic());
//            statement.setDate(7, from.getDob());
//            statement.setDate(8, from.getDob());
//            statement.setDate(9, to.getDob());
//            statement.setDate(10, to.getDob());
//            statement.setString(11, from.getGender() + "");
//            statement.setString(12, from.getGender() + "");
//            statement.setString(13, from.getNationality());
//            statement.setString(14, from.getNationality());
//            statement.setString(15, from.getFamilyState() + "");
//            statement.setString(16, from.getFamilyState() + "");
//            statement.setString(17, from.getCountry());
//            statement.setString(18, from.getCountry());
//            statement.setString(19, from.getCity());
//            statement.setString(20, from.getCity());
//            statement.setString(21, from.getStreetHouseRoom());
//            statement.setString(22, from.getStreetHouseRoom());
//            statement.setString(23, from.getIndexNumber());
//            statement.setString(24, from.getIndexNumber());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return 0;
    }

    public boolean delete(Integer id) throws SQLException {
        CallableStatement statement = connection.prepareCall(SQL_DELETE_CONTACT);

        statement.setInt(1, id);

        return statement.execute();
    }

    public Integer create(Contact entity) throws NamingException {
        CallableStatement statement = null;
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
            ResultSet genKey = statement.executeQuery();
            if (genKey.next()) {
                return genKey.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return null;
    }

    public boolean update(Contact entity) throws SQLException {
        CallableStatement statement = connection.prepareCall(SQL_UPDATE_CONTACT);

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

        return statement.execute();
    }

    public ContactDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public ContactDAO() {
    }
}
