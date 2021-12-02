package ru.core.services.impl;

import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.exeptions.OrderingException;
import ru.core.models.Ordering;
import ru.core.models.OrderingItem;
import ru.core.services.OrderingSerive;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderingServiceImpl implements OrderingSerive {

    private final OrderingDao orderingDao;
    private final OrderingItemDao orderingItemDao;

    public OrderingServiceImpl(OrderingDao orderingDao,
                               OrderingItemDao orderingItemDao) {
        this.orderingDao = orderingDao;
        this.orderingItemDao = orderingItemDao;
    }

    @Override
    public int createOrder(Ordering ordering, Connection connection) throws SQLException {
        try {
            int orderId = orderingDao.createOrder(ordering, connection);

            for (OrderingItem item : ordering.getOrderingItems()) {
                item.setOrderingId(orderId);
                orderingItemDao.addItem(item, connection);
            }

            return orderId;

        } catch (Exception e) {
            connection.rollback();
            throw new OrderingException("create error: ", e);
        }
    }

    @Override
    public int updateOrder(Ordering ordering, Connection connection) throws SQLException {
        return orderingDao.updateOrder(ordering, connection);
    }

    @Override
    public Ordering getOrder(String id, Connection connection) throws SQLException {
        Ordering ordering;
        try {
            ordering = orderingDao.getOrder(id, connection);
            var orderingList = orderingItemDao.findAll(String.valueOf(ordering.getId()), connection);

            if (!orderingList.isEmpty()) {
                ordering.setOrderingItems(orderingList);
            }

        } catch (Exception e) {
            connection.rollback();
            throw new OrderingException("get Ordering error: ", e);
        }

        return ordering;
    }

    @Override
    public void markedOrder(Connection connection) throws SQLException {
        orderingDao.markedOrder(connection);
    }

    @Override
    public void deleteAll(Connection connection) throws SQLException, ClassNotFoundException {
        orderingDao.deleteAll(connection);
    }
}
