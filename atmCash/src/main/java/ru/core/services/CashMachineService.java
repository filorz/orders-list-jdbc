package ru.core.services;

import ru.core.models.CashMachine;
import ru.core.models.Cassette;
import ru.core.models.GroupAtm;

public interface CashMachineService {

    Cassette extraditionBySum(CashMachine cashMachine, long querySum) throws IllegalAccessException;

    long balanceAmount(CashMachine cashMachine);

    String rollbackSavePoint(GroupAtm groupAtm) throws IllegalAccessException;

    void addSavePoint(GroupAtm groupAtm) throws CloneNotSupportedException;

}
