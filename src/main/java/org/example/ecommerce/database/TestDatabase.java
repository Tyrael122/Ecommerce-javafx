package org.example.ecommerce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabase {

    private static final String tempDatabaseName = "temp" + Math.round(Math.random() * 1000);

    private static Connection connection;

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");

        createTempDatabaseIfNotExists();
        createTablesIfNotExists();
    }

    public static void disconnect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }

        deleteTempDatabaseIfExists();

        connection.close();
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }

        return connection;
    }

    private static void createTempDatabaseIfNotExists() throws SQLException {
        DatabaseUtils.createDatabaseIfNotExists(tempDatabaseName, connection);
    }

    private static void createTablesIfNotExists() {
        DatabaseUtils.createTablesIfNotExists(connection);
    }

    private static void deleteTempDatabaseIfExists() {
        try {
            connection.prepareStatement("DROP DATABASE IF EXISTS " + tempDatabaseName).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
