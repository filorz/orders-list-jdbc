package ru.core.dao;

import ru.core.models.Ordering;

import java.sql.SQLException;

public interface OrderingDao {

    boolean createOrder(String userName) throws SQLException;

    boolean updateOrder(Ordering ordering) throws SQLException;

    Ordering getOrder(String id) throws SQLException;

    public void markedOrder() throws SQLException;

    void deleteAll() throws SQLException;

}
