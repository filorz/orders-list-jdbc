package ru.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.exeptions.DataBaseOperationException;
import ru.core.dao.exeptions.OrderingItemException;
import ru.core.models.OrderingItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderingItemDaoImpl implements OrderingItemDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderingItemDaoImpl.class);

    private static final String INSERT_ITEM = "INSERT INTO ordering_items (ordering_id, item_name, item_count, item_price) VALUES  (?, ?, ?, ?);";
    private static final String UPDATE_ITEM_COUNT = "UPDATE ordering_items SET item_count = ? WHERE id = ?";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER = "SELECT * FROM ordering_items WHERE ordering_id = ?";

    @Override
    public int addItem(OrderingItem item, Connection connection) throws IllegalAccessException {
        if (item == null) {
            throw new IllegalAccessException();
        }
        try (var preparedStatement = connection.prepareStatement(INSERT_ITEM, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, item.getOrderingId());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setInt(3, item.getItemCount());
            preparedStatement.setInt(4, Integer.parseInt(item.getItemPrice()));
            preparedStatement.executeUpdate();

            item.setId(item.getOrderingId());
            logger.info("add item for ordering {}", item);

            var rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException ex) {
            throw new DataBaseOperationException("execute insert error", ex);
        }
    }

    @Override
    public int updateItemCount(long entityId, int count, Connection connection) {
        if (count > 0) {
            try (var preparedStatement = connection.prepareStatement(UPDATE_ITEM_COUNT, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, count);
                preparedStatement.setLong(2, entityId);
                preparedStatement.executeUpdate();

                logger.info("update item count {}", count);

                var rs = preparedStatement.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);

            } catch (SQLException ex) {
                throw new DataBaseOperationException("execute update error", ex);
            }

        } else {
            throw new OrderingItemException("wrong count items", new Exception());
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

