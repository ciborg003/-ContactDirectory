package com.itechart.projects.contactDirectory.model.dao;

import com.itechart.projects.contactDirectory.model.entity.Entity;
import java.sql.Connection; 
import java.sql.SQLException; 
import java.sql.Statement; 
import org.apache.log4j.Logger;

public abstract class AbstractDAO <K, T extends Entity> { 
    
    protected static final Logger LOGGER = Logger.getRootLogger();

    public AbstractDAO() {
    }

    public void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("Can't close statement", ex);
        }
    }
} 