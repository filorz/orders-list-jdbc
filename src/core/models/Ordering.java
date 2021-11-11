package core.models;

import java.util.Date;

public class Ordering {

    private int id;
    private String userName;
    private int done;
    private Date updatedAt;
    private OrderingItems orderingItems;

    public Ordering() {
    }

    public Ordering(int id, String itemName, int itemCount, Date updatedAt) {
        this.id = id;
        this.userName = itemName;
        this.done = itemCount;
        this.updatedAt = updatedAt;
    }

    public OrderingItems getOrderingItems() {
        return orderingItems;
    }

    public void setOrderingItems(OrderingItems orderingItems) {
        this.orderingItems = orderingItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
