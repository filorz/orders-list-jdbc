package ru.atm.models;

import ru.atm.exceptions.AtmException;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CashMachine implements Cloneable {
    private static final Logger logger = Logger.getLogger(CashMachine.class.getName());

    public static final int EMPTY_SUM = 0;
    public static final int EMPTY_NOMINAL = 0;

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

    public void setCassetteList(List<Cassette> cassetteList) {
        this.cassetteList.addAll(cassetteList);
    }

    public long getBalanceAmount() {
        int totalSum = 0;
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

    public Cassette extraditionBySum(long querySum) {

        if (cassetteList.isEmpty()) {
            throw new AtmException("Cassette list not found error. ", new Exception());
        }

        long totalSum = getBalanceAmount();
        if (querySum > EMPTY_SUM && querySum <= totalSum) {
            List<Cassette> sortedList = cassetteList.stream()
                    .sorted(Comparator.comparingInt(c -> c.getNominal().getType()))
                    .collect(Collectors.toList());

            for (Cassette cassette : sortedList) {
                int nominal = cassette.getNominal().getType();
                int totalSumCassette = cassette.getCount() * nominal;

                if (querySum <= totalSumCassette && querySum % nominal == EMPTY_NOMINAL) {
                    cassette.setCount(Math.toIntExact(cassette.getCount() - (querySum / nominal)));
                    Cassette removeCassette = null;
                    if (cassetteList != null && !cassetteList.isEmpty()) {
                        removeCassette = cassetteList.stream()
                                .filter(c -> c.getNominal().getType() == nominal).findFirst().get();
                    }
                    cassetteList.remove(removeCassette);
                    cassetteList.add(cassette);

                    logger.info("extradition from cassette select sum: "
                            + querySum + " nominal: " + nominal
                            + " in count: " + cassette.getCount());

                    return new Cassette(
                            cassette.getNominal(),
                            Math.toIntExact(querySum / nominal));
                }
            }
        }

        throw new AtmException("Cassette not find error in CashMachine ", new Exception());
    }

    @Override
    public CashMachine clone() {
        CashMachine cashMachineNew = new CashMachine(this.cassetteList);
        cashMachineNew.setOnlineStatus(this.onlineStatus);

        return cashMachineNew;
    }
}