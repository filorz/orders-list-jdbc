package ru.core.services.impl;

import ru.core.exceptions.CassetteExtraditionException;
import ru.core.models.CashMachine;
import ru.core.models.Cassette;
import ru.core.models.Nominal;
import ru.core.services.CashMachineService;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CashMachineServiceImpl implements CashMachineService {
    private static final Logger logger = Logger.getLogger(CashMachineServiceImpl.class.getName());

    public static final int EMPTY_SUMM = 0;

    @Override
    public Cassette extraditionBySum(CashMachine cashMachine, long querySum) throws IllegalAccessException {
        if (cashMachine.getCassetteList().isEmpty()) {
            throw new IllegalAccessException();
        }

        if (querySum > EMPTY_SUMM && querySum <= cashMachine.getTotalSum()) {
            List<Cassette> sortedList = cashMachine.getCassetteList().stream()
                    .sorted(Comparator.comparingInt(c -> c.getNominal().getType()))
                    .collect(Collectors.toList());

            for (Cassette cassette : sortedList) {
                int nominal = cassette.getNominal().getType();
                int totalSumCassette = cassette.getCount() * nominal;

                    if (querySum <= totalSumCassette && querySum % nominal == 0) {
                        cassette.setCount(Math.toIntExact(cassette.getCount() - (querySum / nominal)));
                        cashMachine.getCassetteList().remove(cashMachine.getCassetteList().stream()
                                .filter(c -> c.getNominal().getType() != Nominal.RUB_50.getType()).findFirst().get());
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
}
