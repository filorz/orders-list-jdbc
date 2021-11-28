package ru.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.exeptions.DataBaseOperationException;
import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderingItemDaoImpl implements OrderingItemDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderingItemDaoImpl.class);

    private static final String INSERT_ITEM = "INSERT INTO ordering_items (ordering_id, item_name, item_count, item_price) VALUES  (?, ?, ?, ?);";
    private static final String UPDATE_ITEM_COUNT = "UPDATE ordering_items SET item_count = ? WHERE id = ?";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER = "SELECT * FROM ordering_items WHERE ordering_id = ?";

    private final Connection connection;

    public OrderingItemDaoImpl(Connection connectorHandle) {
        this.connection = connectorHandle;
    }

    @Override
    public int addItem(Ordering ordering, Connection connection) {
        if (ordering.getOrderingItems() != null && !ordering.getOrderingItems().isEmpty()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEM, Statement.RETURN_GENERATED_KEYS)) {

                for (OrderingItem orderingItem : ordering.getOrderingItems()) {
                    preparedStatement.setInt(1, ordering.getId());
                    preparedStatement.setString(2, orderingItem.getItemName());
                    preparedStatement.setInt(3, orderingItem.getItemCount());
                    preparedStatement.setInt(4, Integer.parseInt(orderingItem.getItemPrice()));
                    preparedStatement.executeUpdate();

                    orderingItem.setId(ordering.getId());
                    logger.info("add item for ordering {}", orderingItem);
                }

                var rs = preparedStatement.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);

            } catch (SQLException ex) {
                throw new DataBaseOperationException("execute insert error", ex);
            }

        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int updateItemCount(String entityId, int count) throws Exception {
        if (count > 0) {
            try (var connection = this.connection;
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_COUNT, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, count);
                preparedStatement.setInt(2, Integer.parseInt(entityId));
                preparedStatement.executeUpdate();
                connection.commit();

                logger.info("update item count {}", count);

                var rs = preparedStatement.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);

            } catch (SQLException ex) {
                throw new DataBaseOperationException("execute update error", ex);
            }

        } else {
            throw new Exception();
        }
    }

    @Override
    public List<OrderingItem> findAll(String id, Connection connection) throws SQLException {
        try (var statement = connection.prepareStatement(SELECT_ORDER_ITEMS_BY_ORDER)) {
            statement.setInt(1, Integer.parseInt(id));
            ResultSet rs = statement.executeQuery();

            List<OrderingItem> orderingList = new ArrayList<>();
            if (rs != null) {
                while (rs.next()) {
                    var orderingItem = new OrderingItem();
                    orderingItem.setOrderingId(rs.getInt("ordering_id"));
                    orderingItem.setId(rs.getInt("id"));
                    orderingItem.setItemName(rs.getString("item_name"));
                    orderingItem.setItemPrice(rs.getString("item_price"));
                    orderingItem.setItemCount(rs.getInt("item_count"));
                    orderingList.add(orderingItem);
                }
            }
            logger.info("find all orders item {}", orderingList);

            return orderingList;
        }
    }
}

