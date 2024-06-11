package org.example.ecommerce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RealDatabase {
    private static Connection connection;

    static {
        connectFirstTime();
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "my-secret-pw");
        DatabaseUtils.createDatabaseIfNotExists("ecommerce", connection);
    }

    public static void disconnect() throws SQLException {
        connection.close();
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }

        return connection;
    }

    private static void connectFirstTime() {
        try {
            connect();

            DatabaseUtils.createTablesIfNotExists(connection);
            DatabaseUtils.createSampleDataIfNotExists();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
