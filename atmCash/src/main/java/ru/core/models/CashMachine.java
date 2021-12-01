package ru.core.models;

import java.util.List;

public class CashMachine {

    private List<Cassette> cassetteList;
    private int totalSum = 0;

    public long getTotalSum() {
        if (cassetteList != null && !cassetteList.isEmpty()) {
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

    public List<Cassette> getCassetteList() {
        return cassetteList;
    }

    public void setCassetteList(List<Cassette> cassetteList) {
        this.cassetteList = cassetteList;
    }

}