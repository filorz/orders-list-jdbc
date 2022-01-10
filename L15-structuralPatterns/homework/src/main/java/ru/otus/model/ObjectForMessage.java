package ru.otus.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage clone() {
        ObjectForMessage objectForMessage = new ObjectForMessage();
        List<String> dataCopy = new ArrayList<>(this.data);
        objectForMessage.setData(dataCopy);

        return objectForMessage;
    }
}
