package core.dao;

import core.config.ConnectorHandle;
import core.models.Ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderingDaoImpl implements BaseDao<Ordering> {

    private static final String INSERT_USERS_SQL = "INSERT INTO ordering (user_name, updated_at) VALUES  (?, ?);";
    private static final String SELECT_USER_BY_ID = "select * from ordering where id =?";
    private static final String SELECT_ALL_USERS = "select * from ordering";
    private static final String DELETE_USERS_SQL = "delete from ordering where id = ?;";
    private static final String DELETE_ALL_SQL = "delete from ordering";
    private static final String UPDATE_USERS_SQL = "update ordering set user_name = ?,updated_at= ? where id = ?;";

    @Override
    public int create(String userName) {

        try (Connection connection = ConnectorHandle.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, "2021-11-09 17:36:05");
            System.out.println(preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }

        return -1;
    }

    @Override
    public boolean update(Ordering ordering) {
        boolean rowUpdated = false;
        try (Connection connection = ConnectorHandle.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, ordering.getUserName());
            statement.setString(2, "2021-11-11 11:11:11");
            statement.setInt(3, ordering.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    @Override
    public Ordering get(String id) {
        try (Connection connection = ConnectorHandle.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setString(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs != null) {
                Ordering ordering = new Ordering();
                while (rs.next()) {
                    ordering.setUserName(rs.getString("user_name"));
                    ordering.setId(Integer.parseInt(rs.getString("id")));
                    ordering.setUpdatedAt(String.valueOf(rs.getString("updated_at")));
                }
                return ordering;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return new Ordering();
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectorHandle.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_SQL)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
