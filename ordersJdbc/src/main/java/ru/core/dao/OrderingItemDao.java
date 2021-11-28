package ru.core.dao;

import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderingItemDao {

    int addItem(Ordering ordering, Connection connection) throws SQLException, ClassNotFoundException;

    int updateItemCount(String entityId, int count) throws Exception;

    List<OrderingItem> findAll(String id, Connection connection) throws SQLException, ClassNotFoundException;

}
