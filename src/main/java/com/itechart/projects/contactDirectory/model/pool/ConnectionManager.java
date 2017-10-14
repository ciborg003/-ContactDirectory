package com.itechart.projects.contactDirectory.model.pool;

import com.itechart.projects.contactDirectory.util.FileWorker;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionManager {
    private static final String propFile = "D:\\ItechArt\\ContactDirectory\\src\\main\\resources\\META-INF\\properties\\DB_Properties.properties";//"src\\main\\resources\\META-INF\\DB_Properties.properties";
    
    public static synchronized Connection getConnection() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        
        Properties properties = FileWorker.getPropertyFile(propFile);
        
        dataSource.setDriverClassName(properties.getProperty("driver"));
        dataSource.setUrl(properties.getProperty("URL"));
        dataSource.setUsername(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setMaxOpenPreparedStatements(100);
        
        return dataSource.getConnection();
    }
    
    public static void closeConnection(Connection connection){
        try {
            //1
            if (connection != null) {
                //2
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //3
    }
}
