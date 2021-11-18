package ru.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.core.config.ConnectorHandle;
import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.impl.OrderingDaoImpl;
import ru.core.dao.impl.OrderingItemDaoImpl;
import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.sql.SQLException;
import java.util.Collections;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String URL = "jdbc:postgresql://localhost:5432/jdbc_ordering";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        ConnectorHandle connectorHandle = new ConnectorHandle(URL, USER, PASSWORD);

        OrderingDao orderingDao = new OrderingDaoImpl(connectorHandle);
        OrderingItemDao orderingItemDao = new OrderingItemDaoImpl(connectorHandle);

        // Create order in DB
//        try {
//            Boolean isCreate = orderingDao.createOrder("NameTest");
//        } catch (SQLException e) {
//            logger.info(e.getMessage());
//        }

        // Add item for Ordering in DB
//        try {
//            Ordering ordering = orderingDao.getOrder("11");
//            OrderingItem orderingItem = new OrderingItem();
//            orderingItem.setOrderingId(ordering.getId());
//            orderingItem.setItemName("Toy");
//            orderingItem.setItemCount(2);
//            orderingItem.setItemPrice("200");
//
//            ordering.setOrderingItems(Collections.singletonList(orderingItem));
//            orderingItemDao.addItem(orderingItem);
//        } catch (SQLException e) {
//            logger.info(e.getMessage());
//        }
//
//        // Update item count for Ordering in DB
//        try {
//            orderingItemDao.updateItemCount("9", 20);
//        } catch (SQLException e) {
//            logger.info(e.getMessage());
//        }

        // Marked all items order
        try {
            orderingDao.markedOrder();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }


//        orderingDao.deleteAll();

//        Ordering newOrdering = orderingDao.get("22");
//        newOrdering.setUserName("New Order");
//        newOrdering.setUpdatedAt(new Date());

//        orderingDao.updateOrder(newOrdering);


    }
}
