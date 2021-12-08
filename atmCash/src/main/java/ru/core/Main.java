package ru.core;

import ru.core.models.CashMachine;
import ru.core.models.Cassette;
import ru.core.models.GroupAtm;
import ru.core.models.Nominal;
import ru.core.services.CashMachineService;
import ru.core.services.impl.CashMachineServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {

        CashMachineService cashMachineService = new CashMachineServiceImpl();

        Cassette cassette1 = new Cassette(Nominal.RUB_50, 10, false);
        Cassette cassette2 = new Cassette(Nominal.RUB_200, 9, false);
        Cassette cassette3 = new Cassette(Nominal.RUB_2000, 10, false);

        Cassette cassette4 = new Cassette(Nominal.RUB_50, 10, false);
        Cassette cassette5 = new Cassette(Nominal.RUB_200, 9, false);
        Cassette cassette6 = new Cassette(Nominal.RUB_2000, 10, false);

        List<Cassette> list = new ArrayList<>();
        List<Cassette> list2 = new ArrayList<>();

        list.add(cassette1);
        list.add(cassette2);
        list.add(cassette3);

        list2.add(cassette4);
        list2.add(cassette5);
        list2.add(cassette6);

        CashMachine cashMachine = new CashMachine(list);
        cashMachine.setOnlineStatus(true);

        CashMachine cashMachine2 = new CashMachine(list2);
        cashMachine2.setOnlineStatus(true);

        List<CashMachine> cashMachineList = new ArrayList<>();
        cashMachineList.add(cashMachine);
        cashMachineList.add(cashMachine2);

        GroupAtm groupAtm = new GroupAtm();
        groupAtm.setCashMachineList(cashMachineList);
        cashMachineService.addSavePoint(groupAtm);

        System.out.println(groupAtm.getActiveSavePoint());
        for (CashMachine item : groupAtm.getCashMachineList()) {
            for (Cassette cassette : item.getCassetteList()) {
                System.out.println(cassette.getNominal() + " " + cassette.getCount());
            }
            System.out.println();
        }

//      Выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
        try {
            Cassette result = cashMachineService.extraditionBySum(groupAtm.getCashMachineList().get(0), 600);
            cashMachineService.addSavePoint(groupAtm);
            System.out.println(result.getNominal() + " " + result.getCount());

            System.out.println(groupAtm.getActiveSavePoint());
            for (CashMachine item : groupAtm.getCashMachineList()) {
                for (Cassette cassette : item.getCassetteList()) {
                    System.out.println(cassette.getNominal() + " " + cassette.getCount());
                }
                System.out.println();
            }

            cashMachineService.rollbackSavePoint(groupAtm);

            System.out.println(groupAtm.getActiveSavePoint());
            for (CashMachine item : groupAtm.getCashMachineList()) {
                for (Cassette cassette : item.getCassetteList()) {
                    System.out.println(cassette.getNominal() + " " + cassette.getCount());
                }
                System.out.println();
            }
            System.out.println();

            for (Map.Entry<String, List<CashMachine>> item : groupAtm.getSavePoint().entrySet()) {
                for (CashMachine value : item.getValue()) {
                    for (Cassette cassette : value.getCassetteList()) {
                        System.out.println(cassette.getNominal() + " " + cassette.getCount());
                    }
                    System.out.println();
                }
            }

        } catch (IllegalAccessException | CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
