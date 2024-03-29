package ru.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.impl.OrderingDaoImpl;
import ru.core.dao.impl.OrderingItemDaoImpl;
import ru.core.datasource.ConnectionHandler;
import ru.core.models.Ordering;
import ru.core.models.OrderingItem;
import ru.core.services.OrderingItemService;
import ru.core.services.OrderingSerive;
import ru.core.services.impl.OrderingItemServiceImpl;
import ru.core.services.impl.OrderingServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String URL = "jdbc:postgresql://localhost:5432/jdbc_ordering";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        ConnectionHandler driverManagerDataSource = new ConnectionHandler(URL, USER, PASSWORD);
        Connection connection = null;
        try {
            connection = driverManagerDataSource.getConnection();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        OrderingItemDao orderingItemDao = new OrderingItemDaoImpl();
        OrderingDao orderingDao = new OrderingDaoImpl();

        OrderingSerive orderingSerive = new OrderingServiceImpl(orderingDao, orderingItemDao);
        OrderingItemService orderingItemService = new OrderingItemServiceImpl(orderingItemDao, orderingDao);

        try {
//         Clean rows in DB
            orderingSerive.deleteAll(connection);

//         Create order in DB and Add item for Ordering in DB
            Ordering ordering = new Ordering("NameTest", 10, LocalDateTime.now());

            OrderingItem orderingItem = new OrderingItem();
            orderingItem.setOrderingId(ordering.getId());
            orderingItem.setItemName("Toy");
            orderingItem.setItemCount(2);
            orderingItem.setItemPrice("200");
            ordering.setOrderingItems(Collections.singletonList(orderingItem));

            int orderId = orderingSerive.createOrder(ordering, connection);

            Ordering orderingFromDb = orderingSerive.getOrder(String.valueOf(orderId), connection);

//         Update item count for Ordering in DB
            orderingItemService.updateItemCount(orderingFromDb,
                    orderingFromDb.getOrderingItems().get(0).getId(), 20, connection);

//         Marked all items order
            orderingSerive.markedOrder(connection);

//         Update ordering entity in DB
            orderingFromDb.setUserName("Update User Name");
            orderingFromDb.setUpdatedAt(LocalDateTime.now());

            orderingSerive.updateOrder(orderingFromDb, connection);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
