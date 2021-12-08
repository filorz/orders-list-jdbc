package ru.core.models;

import java.util.*;

public class CashMachine implements Cloneable {

    private final List<Cassette> cassetteList;
    private boolean onlineStatus = true;

    public CashMachine(List<Cassette> cassetteList) {
        this.cassetteList = cassetteList;
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
        CashMachine cashMachine = (CashMachine) super.clone();
        List<Cassette> cassetteList = new LinkedList<>();

        for (Cassette cassette : this.cassetteList) {
            cassetteList.add(cassette.clone());
        }
        cashMachine.setCassetteList(cassetteList);

        return cashMachine;
    }
}