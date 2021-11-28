package ru.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.core.dao.OrderingDao;
import ru.core.dao.exeptions.DataBaseOperationException;
import ru.core.models.Ordering;

import java.sql.*;
import java.time.LocalDateTime;

public class OrderingDaoImpl implements OrderingDao {

    private static final String INSERT_ORDER_SQL = "INSERT INTO ordering (user_name, updated_at) VALUES  (?, ?);";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM ordering WHERE id = ?";
    private static final String DELETE_ALL_SQL = "TRUNCATE ordering CASCADE";
    private static final String UPDATE_USERS_SQL = "UPDATE ordering SET user_name = ?, updated_at = ? WHERE id = ?";
    private static final String MARKED_ALL_ITEMS = "UPDATE ordering SET done = ?, updated_at = ? WHERE done = false";

    private static final Logger logger = LoggerFactory.getLogger(OrderingDaoImpl.class);

    private final Connection connection;

    public OrderingDaoImpl(Connection connectorHandle) {
        this.connection = connectorHandle;
    }

    @Override
    public int createOrder(Ordering ordering, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime dateTime = LocalDateTime.now();

            preparedStatement.setString(1, ordering.getUserName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));

            preparedStatement.executeUpdate();
            logger.info("create order for name:{}", ordering);

            var rs = preparedStatement.getGeneratedKeys();
            rs.next();
            ordering.setId(rs.getInt(1));

            return rs.getInt(1);

        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeInsert error", ex);
        }
    }

    @Override
    public int updateOrder(Ordering ordering) {
        try (var connection = this.connection;
             var statement = connection.prepareStatement(UPDATE_USERS_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ordering.getUserName());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(3, ordering.getId());

            statement.executeUpdate();
            connection.commit();

            logger.info("update order:{}", ordering);

            var rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeInsert error", ex);
        }
    }

    @Override
    public Ordering getOrder(String id, Connection connection) throws SQLException {
        var ordering = new Ordering();

        try (var statement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            statement.setInt(1, Integer.parseInt(id));

            ResultSet rs = statement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ordering.setUserName(rs.getString("user_name"));
                    ordering.setId(Integer.parseInt(rs.getString("id")));
                    ordering.setUpdatedAt(LocalDateTime.now());
                }
            }
        }
        logger.info("get order:{}", ordering);

        return ordering;
    }

    @Override
    public void deleteAll() throws SQLException {
        try (var connection = this.connection;
             var preparedStatement = connection.prepareStatement(DELETE_ALL_SQL)) {
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("all delete orders");
        }
    }

    @Override
    public void markedOrder() throws SQLException {
        try (var connection = this.connection;
             var preparedStatement = connection.prepareStatement(MARKED_ALL_ITEMS)) {

            preparedStatement.setBoolean(1, Boolean.TRUE);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
            connection.commit();

            logger.info("marker items");
        }
    }
}
