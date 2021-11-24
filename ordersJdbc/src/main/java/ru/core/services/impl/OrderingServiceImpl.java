package ru.core.services.impl;

import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.models.Ordering;
import ru.core.services.OrderingSerive;

import java.sql.SQLException;

public class OrderingServiceImpl implements OrderingSerive {

    private final OrderingDao orderingDao;
    private final OrderingItemDao orderingItemDao;

    public OrderingServiceImpl(OrderingDao orderingDao, OrderingItemDao orderingItemDao) {
        this.orderingDao = orderingDao;
        this.orderingItemDao = orderingItemDao;
    }

    @Override
    public int createOrder(Ordering ordering) throws SQLException, ClassNotFoundException {
        int orderId = orderingDao.createOrder(ordering);
        orderingItemDao.addItem(ordering);

        return orderId;
    }

    @Override
    public int updateOrder(Ordering ordering) throws SQLException {
        return orderingDao.updateOrder(ordering);
    }

    @Override
    public Ordering getOrder(String id) throws SQLException, ClassNotFoundException {
        Ordering ordering = orderingDao.getOrder(id);
        var orderingList = orderingItemDao.findAll(String.valueOf(ordering.getId()));

        if (!orderingList.isEmpty()) {
            ordering.setOrderingItems(orderingList);
        }

        return ordering;
    }

    @Override
    public void markedOrder() throws SQLException {
        orderingDao.markedOrder();
    }

    @Override
    public void deleteAll() throws SQLException, ClassNotFoundException {
        orderingDao.deleteAll();
    }
}
