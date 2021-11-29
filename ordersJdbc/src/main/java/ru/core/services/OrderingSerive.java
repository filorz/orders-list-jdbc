package ru.core.services;

import ru.core.models.Ordering;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderingSerive {

    int createOrder(Ordering ordering, Connection connection) throws SQLException, ClassNotFoundException;

    int updateOrder(Ordering ordering, Connection connection) throws SQLException;

    Ordering getOrder(String id, Connection connection) throws SQLException, ClassNotFoundException;

    void markedOrder(Connection connection) throws SQLException;

    void deleteAll(Connection connection) throws SQLException, ClassNotFoundException;

}
