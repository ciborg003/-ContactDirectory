package com.itechart.projects.contactDirectory.model.dao;

import com.itechart.projects.contactDirectory.model.entity.Entity;
import java.sql.Connection; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
public abstract class AbstractDAO <K, T extends Entity> { 
    public abstract List<T> findAll(); 
    public abstract T findEntityById(K id); 
    public abstract boolean delete(K id); 
    public abstract boolean delete(T entity); 
    public abstract boolean create(T entity); 
    public abstract T update(T entity); 
    
    public void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void closeConnection(Connection connection){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
} 