package ru.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.core.config.ConnectorHandle;
import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.sql.*;
import java.time.LocalDateTime;

public class OrderingItemDaoImpl implements OrderingItemDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderingItemDaoImpl.class);

    private static final String INSERT_ITEM = "INSERT INTO ordering_items (ordering_id, item_name, item_count, item_price) VALUES  (?, ?, ?, ?);";
    private static final String UPDATE_ITEM_COUNT = "UPDATE ordering_items SET item_count = ? WHERE id = ?";

    private final ConnectorHandle connectorHandle;

    public OrderingItemDaoImpl(ConnectorHandle connectorHandle) {
        this.connectorHandle = connectorHandle;
    }

    @Override
    public boolean addItem(OrderingItem orderingItem) throws SQLException {

        if (orderingItem != null && orderingItem.getOrderingId() > 0) {
            try (var connection = connectorHandle.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEM, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setInt(1, orderingItem.getOrderingId());
                preparedStatement.setString(2, orderingItem.getItemName());
                preparedStatement.setInt(3, orderingItem.getItemCount());
                preparedStatement.setInt(4, Integer.parseInt(orderingItem.getItemPrice()));

                preparedStatement.executeUpdate();
                connection.commit();

                logger.info("add item for ordering {}", orderingItem.getId());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean updateItemCount(String entityId, Integer count) throws SQLException {

        if (count > 0) {
            try (var connection = connectorHandle.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_COUNT)) {

                preparedStatement.setInt(1, count);
                preparedStatement.setInt(2, Integer.parseInt(entityId));

                preparedStatement.executeUpdate();
                connection.commit();

                logger.info("update item count {}", count);
                return true;
            }
        }

        return false;
    }
}

