package core.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorHandle {

    private static String jdbcPostgreURL = "jdbc:postgresql://localhost:5432/jdbc_ordering?useSSL=false";
    private static String jdbcPostgresUsername = "postgres";
    private static String jdbcPostgresPassword = "root";

    private static String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_ordering?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "root";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

        return connection;
    }

    public static Connection getConnectionPostgres() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(jdbcPostgreURL, jdbcPostgresUsername, jdbcPostgresPassword);

        return connection;
    }
}
