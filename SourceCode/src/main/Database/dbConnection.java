package main.Database;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class dbConnection {
    private static Connection connection = null;

    /**
     * Gets user's database connection properties from file db.properties
     * then try to connect this program with sql database.
     *
     * @author Vizeard Yeow
     * @return connection
     */
    public static Connection getConnection() {
        try {
            Properties prop = new Properties();
            InputStream in = dbConnection.class.getResourceAsStream("db.properties");
            prop.load(in);
            in.close();

            String driver = prop.getProperty("jdbc.driver");
            String url = prop.getProperty("jdbc.url");
            String username = prop.getProperty("jdbc.username");
            String password = prop.getProperty("jdbc.password");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}