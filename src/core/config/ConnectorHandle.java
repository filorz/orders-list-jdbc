package core.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorHandle {

    private static String jdbcURL = "jdbc:postgresql://localhost:5430/jdbc_ordering";
    private static String jdbcUsername = "postgres";
    private static String jdbcPassword = "root";

    public static Connection getConnectionPostgres() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

        return connection;
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
}
