package com.demoqa.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    static final String url = "jdbc:sqlserver://localhost:51294;databaseName=Bookstore;encrypt=true;trustServerCertificate=true;";
    static final String username = "admin";
    static final String password = "admin";

    public static ResultSet query(String query) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            ResultSet result = conn.prepareStatement(query).executeQuery();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public static boolean verifyConnection() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            LoggingHandler.log("Connected to SQL Server successfully!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
