package ru.core.models;

public class OrderingItem {

    private int id;
    private int orderingId;
    private String itemName;
    int itemCount;
    String itemPrice;

    public OrderingItem(int id, int orderingId, String itemName, int itemCount, String itemPrice) {
        this.id = id;
        this.orderingId = orderingId;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
    }

    public OrderingItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderingId() {
        return orderingId;
    }

    public void setOrderingId(int orderingId) {
        this.orderingId = orderingId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

}
