package com.itechart.projects.contactDirectory.model.dao;

import com.itechart.projects.contactDirectory.model.entity.Entity;
import java.sql.Connection; 
import java.sql.SQLException; 
import java.sql.Statement; 

public abstract class AbstractDAO <K, T extends Entity> { 

    public void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
} 