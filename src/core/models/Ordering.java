package core.models;

public class Ordering {

    private int id;
    private String userName;
    private int done;
    private String updatedAt;

    public Ordering() {
    }

    public Ordering(int id, String itemName, int itemCount, String itemPrice) {
        this.id = id;
        this.userName = itemName;
        this.done = itemCount;
        this.updatedAt = itemPrice;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
