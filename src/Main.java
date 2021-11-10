import core.dao.BaseDao;
import core.dao.OrderingDaoImpl;
import core.models.Ordering;

public class Main {
    public static void main(String[] args) {
        BaseDao orderingDao = new OrderingDaoImpl();
//        orderingDao.deleteAll();

        // Create order in DB
//        orderingDao.create("NameTest");

        // Update order in DB
        Ordering newOrdering = orderingDao.get("22");
        newOrdering.setUserName("New Order");
        newOrdering.setUpdatedAt("date");

        orderingDao.update(newOrdering);


    }
}
