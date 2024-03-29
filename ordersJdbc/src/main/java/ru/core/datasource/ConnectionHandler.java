package ru.core.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionHandler implements DataSource {
    private DataSource dataSourcePool;

    public ConnectionHandler(String url, String user, String pwd) {
        createConnectionPool(url, user, pwd);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnectionAutoCommit();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnectionAutoCommit();
    }

    @Override
    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getLoginTimeout() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        throw new UnsupportedOperationException();
    }

    private void createConnectionPool(String url, String user, String pwd) {
        var config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setConnectionTimeout(3000); //ms
        config.setIdleTimeout(60000); //ms
        config.setMaxLifetime(600000);//ms
        config.setAutoCommit(false);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setPoolName("DemoHiPool");
        config.setRegisterMbeans(true);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setUsername(user);
        config.setPassword(pwd);

        dataSourcePool = new HikariDataSource(config);
    }

    private Connection getConnectionAutoCommit() throws SQLException {
        var connection = dataSourcePool.getConnection();
        connection.setAutoCommit(true);

        return connection;
    }
}
