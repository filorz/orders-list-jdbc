package ru.atm;

import ru.atm.models.CashMachine;
import ru.atm.models.Cassette;
import ru.atm.models.Nominal;
import ru.atm.models.GroupAtm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {

        Cassette cassette1 = new Cassette(Nominal.RUB_50, 10);
        Cassette cassette2 = new Cassette(Nominal.RUB_200, 9);
        Cassette cassette3 = new Cassette(Nominal.RUB_2000, 8);

        Cassette cassette4 = new Cassette(Nominal.RUB_50, 5);
        Cassette cassette5 = new Cassette(Nominal.RUB_200, 2);
        Cassette cassette6 = new Cassette(Nominal.RUB_2000, 5);

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
        groupAtm.addSavePoint();
        groupAtm.addSavePoint();

        System.out.println(groupAtm.getActiveSavePoint());
        System.out.println("Base Status: ");
        for (CashMachine item : groupAtm.getCashMachineList()) {
            System.out.println("ATM: " + item.getBalanceAmount());
            for (Cassette cassette : item.getCassetteList()) {
                System.out.println(cassette.getNominal() + " " + cassette.getCount());
            }
            System.out.println();
        }

//      Выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
        Cassette result = cashMachine.extraditionBySum(600);
        groupAtm.addSavePoint();

        System.out.println(result.getNominal() + " " + result.getCount() + " - Extradition count");
        System.out.println();

        System.out.println(groupAtm.getActiveSavePoint());
        System.out.println("Extradition status and Save point: ");
        for (CashMachine item : groupAtm.getCashMachineList()) {
            System.out.println("ATM: " + item.getBalanceAmount());
            for (Cassette cassette : item.getCassetteList()) {
                System.out.println(cassette.getNominal() + " " + cassette.getCount());
            }
            System.out.println();
        }

        groupAtm.rollbackSavePoint();

        System.out.println(groupAtm.getActiveSavePoint());
        for (CashMachine item : groupAtm.getCashMachineList()) {
            System.out.println("ATM: " + item.getBalanceAmount());
            for (Cassette cassette : item.getCassetteList()) {
                System.out.println(cassette.getNominal() + " " + cassette.getCount());
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("All save points: ");
            for (Map.Entry<String, List<CashMachine>> item : groupAtm.getSavePoint().entrySet()) {
                for (CashMachine value : item.getValue()) {
                    System.out.println("ATM: " + value.getBalanceAmount());
                    for (Cassette cassette : value.getCassetteList()) {
                        System.out.println(cassette.getNominal() + " " + cassette.getCount());
                    }
                    System.out.println();
                }
            }

    }
}
