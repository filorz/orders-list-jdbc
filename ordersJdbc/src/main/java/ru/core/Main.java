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
import java.time.LocalDateTime;
import java.util.Collections;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String URL = "jdbc:postgresql://localhost:5432/jdbc_ordering";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        ConnectorHandle connectorHandle = new ConnectorHandle(URL, USER, PASSWORD);

        OrderingItemDao orderingItemDao = new OrderingItemDaoImpl(connectorHandle);
        OrderingDao orderingDao = new OrderingDaoImpl(connectorHandle, orderingItemDao);

        try {
//         Clean rows in DB
            orderingDao.deleteAll();

//         Create order in DB and Add item for Ordering in DB
            Ordering ordering = new Ordering("NameTest", 10, LocalDateTime.now());

            OrderingItem orderingItem = new OrderingItem();
            orderingItem.setOrderingId(ordering.getId());
            orderingItem.setItemName("Toy");
            orderingItem.setItemCount(2);
            orderingItem.setItemPrice("200");
            ordering.setOrderingItems(Collections.singletonList(orderingItem));

            int orderId = orderingDao.createOrder(ordering);

            Ordering orderingFromDb = orderingDao.getOrder(String.valueOf(orderId));

//         Update item count for Ordering in DB
            orderingItemDao.updateItemCount(String.valueOf(orderingFromDb.getOrderingItems().get(0).getId()), 20);

//         Marked all items order
            orderingDao.markedOrder();

//         Update ordering entity in DB
            orderingFromDb.setUserName("Update User Name");
            orderingFromDb.setUpdatedAt(LocalDateTime.now());

            orderingDao.updateOrder(orderingFromDb);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
