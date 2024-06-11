package org.example.ecommerce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static boolean isTest = isRunningJUnitTest();

    public static void connect() throws SQLException {
        if (isTest) {
            TestDatabase.connect();
        } else {
            RealDatabase.connect();
        }
    }

    public static void disconnect() throws SQLException {
        if (isTest) {
            TestDatabase.disconnect();
        } else {
            RealDatabase.disconnect();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (isTest) {
            return TestDatabase.getConnection();
        } else {
            return RealDatabase.getConnection();
        }
    }

    private static boolean isRunningJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }

        return false;
    }
}