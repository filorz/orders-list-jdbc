package ru.atm.models;

import ru.atm.exceptions.AtmException;

import java.util.*;
import java.util.logging.Logger;

public class CashMachine implements Cloneable {
    private static final Logger logger = Logger.getLogger(CashMachine.class.getName());

    public static final int EMPTY_SUM = 0;

    private String id;
    private List<Cassette> cassetteList;
    private boolean onlineStatus = true;

    public CashMachine(List<Cassette> cassetteList) {
        this.id = UUID.randomUUID().toString();
        this.cassetteList = cassetteList;
    }

    public String getId() {
        return id;
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
        this.cassetteList = cassetteList;
    }

    public long getBalanceAmount() {
        int totalSum = 0;
        if (cassetteList != null && !cassetteList.isEmpty()) {
            int nominal;
            int count;

            for (Cassette cassette : cassetteList) {
                nominal = cassette.getBanknote().getNominal().getType();
                count = cassette.getCount();
                totalSum += nominal * count;
            }
        }

        return totalSum;
    }

    public List<Banknote> extraditionBySum(long querySum) {

        if (cassetteList != null && cassetteList.isEmpty()) {
            throw new AtmException("Cassette list not found error. ", new Exception());
        }

        long totalSum = getBalanceAmount();
        if (querySum > EMPTY_SUM && querySum <= totalSum) {
            List<Banknote> banknoteList = new ArrayList<>();
            NavigableSet<Cassette> cassetteSet =
                    new TreeSet<Cassette>(Comparator.comparing(o -> o.getBanknote().getNominal().getType())).descendingSet();
            cassetteSet.addAll(cassetteList);

            for (Cassette cassette : cassetteSet) {
                long nominal = cassette.getBanknote().getNominal().getType();
                long totalSumCassette = cassette.getCount() * nominal;
                double notesNeeded = Math.floor(querySum / nominal);
                boolean isCountNeeded = notesNeeded > 0 ? notesNeeded * nominal <= totalSumCassette : false;

                if (isCountNeeded) {
                    int counter = (int) notesNeeded;

                    while (counter > 0) {
                        Banknote banknote = new Banknote(cassette.getBanknote().getNominal());
                        banknoteList.add(banknote);
                        counter--;
                    }

                    Cassette removeCassette = null;
                    cassette.setCount(Math.toIntExact(cassette.getCount() - (querySum / nominal)));
                    querySum -= nominal * notesNeeded;

                    if (!cassetteList.isEmpty()) {
                        removeCassette = cassetteList.stream()
                                .filter(c -> c.getBanknote().getNominal().getType() == nominal).findFirst().get();
                    }
                    cassetteList.remove(removeCassette);
                    cassetteList.add(cassette);

                    logger.info("extradition from cassette select sum: "
                            + querySum + " nominal: " + nominal
                            + " in count: " + cassette.getCount());
                }
            }

            return banknoteList;
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