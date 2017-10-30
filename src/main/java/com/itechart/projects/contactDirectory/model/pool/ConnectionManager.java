package com.itechart.projects.contactDirectory.model.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class ConnectionManager {

    private static final String propFile = "DB_Properties";
    private static final Logger LOGGER = Logger.getRootLogger();

    public static synchronized Connection getConnection() {
        BasicDataSource dataSource = new BasicDataSource();
        ResourceBundle bundle = ResourceBundle.getBundle(propFile);

        dataSource.setDriverClassName(bundle.getString("driver"));
        dataSource.setUrl(bundle.getString("URL"));
        dataSource.setUsername(bundle.getString("user"));
        dataSource.setPassword(bundle.getString("password"));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(bundle.getString("max_pool")));
        LOGGER.info("Max waitMIllis: " + dataSource.getMaxWaitMillis());

        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException ex) {
            LOGGER.error("Can't get connection to DB", ex);
        } 
        
        return null;
    }

    public static void closeConnection(Connection connection) {
        try {
            //1
            if (connection != null) {
                //2
                connection.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("Can't close connection", ex);
        }
        //3
    }
}