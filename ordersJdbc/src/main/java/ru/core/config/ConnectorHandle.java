package ru.core.config;

import ru.core.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectorHandle {

    private final String url;
    private final String user;
    private final String password;

    public ConnectorHandle(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        var dataSource = new DriverManagerDataSource(url, user, password);

        return dataSource.getConnection();
    }
}
