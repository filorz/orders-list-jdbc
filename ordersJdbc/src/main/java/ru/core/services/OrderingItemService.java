package ru.core.services;

import ru.core.models.Ordering;
import java.sql.Connection;

public interface OrderingItemService {

    int updateItemCount(Ordering ordering, long itemId, int count, Connection connection) throws Exception;

}
