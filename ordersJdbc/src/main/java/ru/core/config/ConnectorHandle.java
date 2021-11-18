package ru.core.config;

import ru.core.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectorHandle {

    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public ConnectorHandle(String url, String user, String password) {
        URL = url;
        USER = user;
        PASSWORD = password;
    }

    public Connection getConnection() throws SQLException {
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);

        return dataSource.getConnection();
    }
}
