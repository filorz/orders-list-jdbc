import core.dao.OrderingDao;
import core.dao.OrderingDaoImpl;
import core.models.Ordering;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        OrderingDao orderingDao = new OrderingDaoImpl();
//        orderingDao.deleteAll();

        // Create order in DB
//        orderingDao.create("NameTest");

        // Update order in DB
        Ordering newOrdering = (Ordering) orderingDao.get("22");
        newOrdering.setUserName("New Order");
        newOrdering.setUpdatedAt(new Date());

        orderingDao.update(newOrdering);


    }
}
