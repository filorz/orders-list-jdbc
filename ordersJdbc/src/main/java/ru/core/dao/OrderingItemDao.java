package ru.core.dao;

import ru.core.models.OrderingItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderingItemDao {

    int addItem(OrderingItem item, Connection connection) throws SQLException, ClassNotFoundException, IllegalAccessException;

    int updateItemCount(long entityId, int count, Connection connection) throws Exception;

    List<OrderingItem> findAll(String id, Connection connection) throws SQLException, ClassNotFoundException;

}
