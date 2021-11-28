package ru.core.services.impl;

import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.exeptions.OrderingException;
import ru.core.models.Ordering;
import ru.core.services.OrderingSerive;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderingServiceImpl implements OrderingSerive {

    private final OrderingDao orderingDao;
    private final OrderingItemDao orderingItemDao;
    private final Connection connection;

    public OrderingServiceImpl(OrderingDao orderingDao,
                               OrderingItemDao orderingItemDao,
                               Connection connection) {
        this.orderingDao = orderingDao;
        this.orderingItemDao = orderingItemDao;
        this.connection = connection;
    }

    @Override
    public int createOrder(Ordering ordering) throws SQLException {
        try (var connection = this.connection) {
            try {
                int orderId = orderingDao.createOrder(ordering, connection);
                orderingItemDao.addItem(ordering, connection);
                connection.commit();

                return orderId;

            } catch (Exception e) {
                connection.rollback();
                throw new OrderingException("create error: ", e);
            }
        }
    }

    @Override
    public int updateOrder(Ordering ordering) throws SQLException {
        return orderingDao.updateOrder(ordering);
    }

    @Override
    public Ordering getOrder(String id) throws SQLException, ClassNotFoundException {
        Ordering ordering;

        try (var connection = this.connection) {
            ordering = orderingDao.getOrder(id, connection);
            var orderingList = orderingItemDao.findAll(String.valueOf(ordering.getId()), connection);

            if (!orderingList.isEmpty()) {
                ordering.setOrderingItems(orderingList);
            }
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
