package ru.core.dao;

import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.sql.SQLException;

public interface OrderingItemDao {

    boolean addItem(OrderingItem orderingItem) throws SQLException;

    boolean updateItemCount(String entityId, Integer count) throws SQLException;

}
