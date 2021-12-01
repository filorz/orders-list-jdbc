package ru.core.models;

public enum Nominal {

    RUB_2000(2000),
    RUB_1000(1000),
    RUB_500(500),
    RUB_200(200),
    RUB_100(100),
    RUB_50(50);

    private int type;

    Nominal(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}