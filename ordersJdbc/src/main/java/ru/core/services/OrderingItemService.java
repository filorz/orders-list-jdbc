package ru.core.services;

import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderingItemService {

    int updateItemCount(String entityId, int count) throws Exception;

}
