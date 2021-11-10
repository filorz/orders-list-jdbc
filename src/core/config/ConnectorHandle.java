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

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException var3) {
            var3.printStackTrace();
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
        }

        return connection;
    }

    public static Connection getConnectionPostgres() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcPostgreURL, jdbcPostgresUsername, jdbcPostgresPassword);
        } catch (SQLException var3) {
            var3.printStackTrace();
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
        }

        return connection;
    }
}
