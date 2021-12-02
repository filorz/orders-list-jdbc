package ru.core.models;

import java.time.LocalDateTime;
import java.util.List;

public class Ordering {

    private long id;
    private String userName;
    private int done;
    private LocalDateTime updatedAt;
    private List<OrderingItem> orderingItems;

    public Ordering() {
    }

    public Ordering(String itemName, int itemCount, LocalDateTime updatedAt) {
        this.userName = itemName;
        this.done = itemCount;
        this.updatedAt = updatedAt;
    }

    public List<OrderingItem> getOrderingItems() {
        return orderingItems;
    }

    public void setOrderingItems(List<OrderingItem> orderingItems) {
        this.orderingItems = orderingItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
