package ru.core.models;

public enum ModeAtm {
    ONLINE("online"),
    OFFLINE("offline");

    ModeAtm(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
