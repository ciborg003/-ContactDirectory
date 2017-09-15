package com.itechart.projects.contactDirectory.model.dao;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import com.itechart.projects.contactDirectory.model.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import org.eclipse.persistence.internal.helper.Helper;

public class ContactDAO extends AbstractDAO<Integer, Contact>{

    public static final String SQL_SELECT_ALL_ABONENTS = "select contact.idContact, contact.name, contact.surname, contact.patronymic, contact.dob, contact.gender, nationality.nation, \n" +
"familystate.familyState, job.job, country.country, city.city, address.streetHouseRoom, email.email \n" +
"from contact join nationality on contact.idNationality = nationality.idNationality\n" +
"join job on job.idJob = contact.idJob\n" +
"join email on contact.idEmail = email.idemail\n" +
"join familystate on contact.idFamilyState = familystate.idFamilyState\n" +
"join address on address.idAddress = contact.idContact\n" +
"join country on country.idCountry = address.idCountry\n" +
"join city on city.idCity = address.idCity;";
    
    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ABONENTS);
            
            while (resultSet.next()){
                Contact contact = new Contact();
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setSurname(resultSet.getString(3));
                contact.setPatronymic(resultSet.getString(4));
//                contact.setDob(resultSet.getDate(5));
                contact.setGender(resultSet.getString(6).charAt(0));
                contact.setNationality(resultSet.getString(7));
                contact.setFamilyState(resultSet.getString(8));
                contact.setJob(resultSet.getString(9));
                contact.setCountry(resultSet.getString(10));
                contact.setCity(resultSet.getString(11));
                contact.setStreetHouseRoom(resultSet.getString(12));
                contact.setEmail(resultSet.getString(13));
                
                contacts.add(contact);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeStatement(statement);   
        }
        
        return contacts;
    }

    @Override
    public Contact findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Contact entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Contact entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Contact update(Contact entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
