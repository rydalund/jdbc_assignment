package se.ecutb.magnus.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    public static Connection getConnection() throws SQLException{

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/connection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String URL = properties.getProperty("url");
        String USER = properties.getProperty("user");
        String PASSWORD = properties.getProperty("password");

        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}

