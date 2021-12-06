package ru.core.models;

import java.util.*;

public class CashMachine implements Cloneable {

    private List<Cassette> cassetteList;
    private int totalSum = 0;
    private boolean onlineStatus = true;

    public long getTotalSum() {
        if (cassetteList != null && !cassetteList.isEmpty()) {
            totalSum = 0;
            int nominal;
            int count;

            for (Cassette cassette : cassetteList) {
                nominal = cassette.getNominal().getType();
                count = cassette.getCount();
                totalSum += nominal * count;
            }
        }

        return totalSum;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public List<Cassette> getCassetteList() {
        return cassetteList;
    }

    public void setCassetteList(List<Cassette> cassetteList) throws CloneNotSupportedException {
        List<Cassette> list = new ArrayList<>();
        for (Cassette cassette : cassetteList) {
            list.add(cassette.clone());
        }
        this.cassetteList.addAll(list);
    }

    @Override
    public CashMachine clone() throws CloneNotSupportedException {
        return (CashMachine) super.clone();
    }
}