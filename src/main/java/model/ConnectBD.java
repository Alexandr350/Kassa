package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBD {
    private static final String URL = "jdbc:mysql://localhost:3306/assortment";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private static ConnectBD instance;

    private ConnectBD() {
    }

    public static ConnectBD getInstance() {
        if (instance == null) {
            instance = new ConnectBD();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
