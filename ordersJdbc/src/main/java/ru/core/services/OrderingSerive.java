package ru.core.services;

import ru.core.models.Ordering;
import java.sql.SQLException;

public interface OrderingSerive {

    int createOrder(Ordering ordering) throws SQLException, ClassNotFoundException;

    int updateOrder(Ordering ordering) throws SQLException;

    Ordering getOrder(String id) throws SQLException, ClassNotFoundException;

    void markedOrder() throws SQLException;

    void deleteAll() throws SQLException, ClassNotFoundException;

}
