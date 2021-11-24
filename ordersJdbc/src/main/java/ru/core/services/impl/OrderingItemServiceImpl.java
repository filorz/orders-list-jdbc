package ru.core.services.impl;

import ru.core.dao.OrderingItemDao;
import ru.core.services.OrderingItemService;

public class OrderingItemServiceImpl implements OrderingItemService {

    private final OrderingItemDao orderingItemDao;

    public OrderingItemServiceImpl(OrderingItemDao orderingItemDao) {
        this.orderingItemDao = orderingItemDao;
    }

    @Override
    public int updateItemCount(String entityId, int count) throws Exception {
        return orderingItemDao.updateItemCount(entityId, count);
    }
}
