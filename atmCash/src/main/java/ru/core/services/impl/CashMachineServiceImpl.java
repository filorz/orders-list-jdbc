package ru.core.services.impl;

import ru.core.exceptions.CassetteExtraditionException;
import ru.core.models.CashMachine;
import ru.core.models.Cassette;
import ru.core.models.GroupAtm;
import ru.core.services.CashMachineService;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CashMachineServiceImpl implements CashMachineService {
    private static final Logger logger = Logger.getLogger(CashMachineServiceImpl.class.getName());

    public static final int EMPTY_SUM = 0;
    public static final int EMPTY_NOMINAL = 0;

    @Override
    public Cassette extraditionBySum(CashMachine cashMachine, long querySum) throws IllegalAccessException {
        if (cashMachine.getCassetteList().isEmpty()) {
            throw new IllegalAccessException();
        }

        if (querySum > EMPTY_SUM && querySum <= cashMachine.getTotalSum()) {
            List<Cassette> sortedList = cashMachine.getCassetteList().stream()
                    .sorted(Comparator.comparingInt(c -> c.getNominal().getType()))
                    .collect(Collectors.toList());

            for (Cassette cassette : sortedList) {
                int nominal = cassette.getNominal().getType();
                int totalSumCassette = cassette.getCount() * nominal;

                    if (querySum <= totalSumCassette && querySum % nominal == EMPTY_NOMINAL) {
                        cassette.setCount(Math.toIntExact(cassette.getCount() - (querySum / nominal)));
                        cashMachine.getCassetteList().remove(cashMachine.getCassetteList().stream()
                                .filter(c -> c.getNominal().getType() == nominal).findFirst().get());
                        cashMachine.getCassetteList().add(cassette);

                        logger.info("extradition from cassette select sum: "
                                + querySum + " nominal: " + nominal
                                + " in count: " + cassette.getCount());

                        return new Cassette(
                                cassette.getNominal(),
                                Math.toIntExact(querySum / nominal),
                                true);
                    }
            }
        }

        throw new CassetteExtraditionException("cassette not find error in CashMachine", new Exception());
    }

    @Override
    public long balanceAmount(CashMachine cashMachine) {
        return cashMachine.getTotalSum();
    }

    @Override
    public String rollbackSavePoint(GroupAtm groupAtm) throws IllegalAccessException {
        if (!groupAtm.getSavePoint().isEmpty() && groupAtm.getSavePoint().size() > 1) {
            List<CashMachine> prevPoint = new ArrayList<>();
            String activePoint = "";

            for (Map.Entry<String, List<CashMachine>> item : groupAtm.getSavePoint().entrySet()) {
                if (item.getKey().equals(groupAtm.getActiveSavePoint())) {
                    groupAtm.setCashMachineList(prevPoint);
                    groupAtm.setActiveAtm(activePoint);
                    return activePoint;
                } else {
                    prevPoint = item.getValue();
                    activePoint = item.getKey();
                }
            }
        }

        throw new IllegalAccessException();
    }

    public void addSavePoint(GroupAtm groupAtm) throws CloneNotSupportedException {
        String uuid = UUID.randomUUID().toString();
        List<CashMachine> listNew = new ArrayList<>();
        for (CashMachine cashMachine : groupAtm.getCashMachineList()) {
            listNew.add(cashMachine.clone());
        }

        groupAtm.setActiveAtm(uuid);
        groupAtm.setSavePoint(uuid, listNew);
    }
}
