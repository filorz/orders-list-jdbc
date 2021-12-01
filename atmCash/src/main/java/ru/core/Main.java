package ru.core;

import ru.core.models.CashMachine;
import ru.core.models.Cassette;
import ru.core.models.Nominal;
import ru.core.services.CashMachineService;
import ru.core.services.impl.CashMachineServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CashMachineService cashMachineService = new CashMachineServiceImpl();

        Cassette cassette2 = new Cassette(Nominal.RUB_200, 9, false);

        Cassette cassette3 = new Cassette(Nominal.RUB_2000, 10, false);

        List<Cassette> list = new ArrayList<>();

        list.add(cassette3);
        list.add(cassette2);

        CashMachine cashMachine = new CashMachine();
        cashMachine.setCassetteList(list);

//      Выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
        try {
//            System.out.println(cashMachine.getCassetteList().stream()
//                    .filter(e -> e.getNominal().getType() == Nominal.RUB_200.getType())
//                    .findFirst().get().getCount());

            Cassette result = cashMachineService.extraditionBySum(cashMachine, 600);
            System.out.println(result.getNominal() + " " + result.getCount());

//            System.out.println(cashMachine.getCassetteList().stream()
//                    .filter(e -> e.getNominal().getType() == Nominal.RUB_200.getType())
//                    .findFirst().get().getCount());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
