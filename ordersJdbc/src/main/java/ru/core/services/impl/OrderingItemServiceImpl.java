package ru.core.services.impl;

import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.exeptions.OrderingException;
import ru.core.models.Ordering;
import ru.core.services.OrderingItemService;

import java.sql.Connection;

public class OrderingItemServiceImpl implements OrderingItemService {

    private final OrderingItemDao orderingItemDao;
    private final OrderingDao orderingDao;

    public OrderingItemServiceImpl(OrderingItemDao orderingItemDao, OrderingDao orderingDao) {
        this.orderingItemDao = orderingItemDao;
        this.orderingDao = orderingDao;
    }

    @Override
    public int updateItemCount(Ordering ordering, long itemId, int count, Connection connection) throws Exception {
        try {
            orderingDao.updateOrder(ordering, connection);
            return orderingItemDao.updateItemCount(itemId, count, connection);

        } catch (Exception e) {
            connection.rollback();
            throw new OrderingException("update Item error: ", e);
        }
    }
}
