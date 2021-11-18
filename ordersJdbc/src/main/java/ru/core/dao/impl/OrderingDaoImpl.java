package ru.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.core.config.ConnectorHandle;
import ru.core.dao.OrderingDao;
import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderingDaoImpl implements OrderingDao {

    private static final String INSERT_ORDER_SQL = "INSERT INTO ordering (user_name, updated_at) VALUES  (?, ?);";
    private static final String SELECT_ORDER_BY_ID = "select * from ordering where id = ?";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER = "select * from ordering_items where ordering_id = ?";
    private static final String DELETE_ALL_SQL = "delete from ordering";
    private static final String UPDATE_USERS_SQL = "update ordering set user_name = ?,updated_at= ? where id = ?;";
    private static final String MARKED_ALL_ITEMS = "UPDATE ordering SET done = ? WHERE done = false";

    private static final Logger logger = LoggerFactory.getLogger(OrderingDaoImpl.class);

    private final ConnectorHandle connectorHandle;

    public OrderingDaoImpl(ConnectorHandle connectorHandle) {
        this.connectorHandle = connectorHandle;
    }

    @Override
    public boolean createOrder(String name) throws SQLException {
        try (var connection = connectorHandle.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL)) {
            LocalDateTime dateTime = LocalDateTime.now();

            preparedStatement.setString(1, name);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));
            System.out.println(preparedStatement);
            preparedStatement.execute();
            connection.commit();
            logger.info("create order for name:{}", name);

            return true;
        }
    }

    @Override
    public boolean updateOrder(Ordering ordering) throws SQLException {
        boolean rowUpdated = false;
        try (Connection connection = connectorHandle.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, ordering.getUserName());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(3, ordering.getId());

            rowUpdated = statement.executeUpdate() > 0;

            logger.info("update order:{}", ordering.getId());
        }

        return rowUpdated;
    }

    @Override
    public Ordering getOrder(String id) throws SQLException {
        var ordering = new Ordering();

        try (var connection = connectorHandle.getConnection();
             var statement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            statement.setInt(1, Integer.parseInt(id));

            ResultSet rs = statement.executeQuery();
            connection.commit();

            if (rs != null) {
                while (rs.next()) {
                    ordering.setUserName(rs.getString("user_name"));
                    ordering.setId(Integer.parseInt(rs.getString("id")));
                    ordering.setUpdatedAt(LocalDateTime.now());
                }
            }
        }

        try (var connection = connectorHandle.getConnection();
             var statement = connection.prepareStatement(SELECT_ORDER_ITEMS_BY_ORDER)) {
            statement.setInt(1, Integer.parseInt(id));

            ResultSet rs = statement.executeQuery();
            connection.commit();

            List<OrderingItem> itemList = new ArrayList<>();

            if (rs != null) {
                while (rs.next()) {
                    OrderingItem orderingItem = new OrderingItem();
                    orderingItem.setOrderingId(rs.getInt("ordering_id"));
                    orderingItem.setItemName(rs.getString("item_name"));
                    orderingItem.setItemPrice(rs.getString("item_price"));
                    orderingItem.setItemCount(rs.getInt("item_count"));

                    itemList.add(orderingItem);
                }

                if (!itemList.isEmpty()) {
                    ordering.setOrderingItems(itemList);
                }
            }
        }

        return ordering;
    }

    @Override
    public void deleteAll() throws SQLException {
        try (Connection connection = connectorHandle.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_SQL)) {
            preparedStatement.executeUpdate();

        }
    }

    @Override
    public void markedOrder() throws SQLException {
        try (var connection = connectorHandle.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(MARKED_ALL_ITEMS)) {

            preparedStatement.setBoolean(1, Boolean.TRUE);
            preparedStatement.executeUpdate();
            connection.commit();
        }
    }
}
