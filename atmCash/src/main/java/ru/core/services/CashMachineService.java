package ru.core.services;

import ru.core.models.CashMachine;
import ru.core.models.Cassette;

public interface CashMachineService {

    Cassette extraditionBySum(CashMachine cashMachine, long querySum) throws IllegalAccessException;

}
