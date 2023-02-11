package ru.atm.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.atm.exceptions.AtmException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GroupAtmTest {

    public static final int QUERY_SUM = 450;
    private GroupAtm groupAtm;
    private CashMachine cashMachine;
    private CashMachine cashMachine2;
    private List<Cassette> cassetteListFirst = new ArrayList<>();
    private List<Cassette> cassetteListSecond = new ArrayList<>();

    @BeforeEach
    void setUp() {
        setEntities();
    }

    @Test
    public void addSavePoint_savePointAdded() throws CloneNotSupportedException {
        groupAtm.addSavePoint();

        assertNotNull(groupAtm.getActiveSavePoint());
        assertFalse(groupAtm.getSavePoint().isEmpty());
    }

    @Test
    public void rollBackAtm_Success() throws CloneNotSupportedException {
        groupAtm.addSavePoint();

        CashMachine cashMachine = groupAtm.getCashMachineList().stream().findFirst().get();
        cashMachine.extraditionBySum(QUERY_SUM);
        groupAtm.addSavePoint();

        String uuidSavePoint = groupAtm.getActiveSavePoint();
        List<CashMachine> savePointByUuid = groupAtm.getSavePointByUuid(uuidSavePoint);
        List<CashMachine> cashMachineList = groupAtm.getCashMachineList();
        assertFalse(savePointByUuid.isEmpty());

        groupAtm.rollbackSavePoint();
        List<CashMachine> cashMachineListRollBack = groupAtm.getCashMachineList();

        assertNotEquals(cashMachineList, cashMachineListRollBack);
    }

    @Test
    public void rollBackAtm_FailedThrow() throws CloneNotSupportedException {
        groupAtm.addSavePoint();

        String uuidSavePoint = groupAtm.getActiveSavePoint();
        List<CashMachine> savePointByUuid = groupAtm.getSavePointByUuid(uuidSavePoint);
        assertFalse(savePointByUuid.isEmpty());

        assertThrows(AtmException.class, () -> groupAtm.rollbackSavePoint());
    }

    @Test
    public void rollBackAtmEmptyList_FailedThrow() {
        groupAtm.setCashMachineList(null);
        assertThrows(AtmException.class, () -> groupAtm.addSavePoint());
    }

    @Test
    public void changeOnlineMode_SuccessChange() {
        CashMachine cashMachine = groupAtm.getCashMachineList().stream().findAny().get();
        boolean modeBeforeChange = cashMachine.isOnlineStatus();

        groupAtm.changeOnlineMode();
        CashMachine cashMachine2 = groupAtm.getCashMachineList().stream().findAny().get();
        boolean modeAfterChange = cashMachine2.isOnlineStatus();

        assertNotEquals(modeBeforeChange, modeAfterChange);
    }

    @Test
    public void getBalanceAmount_SuccessGetBalance() {
        Map<String, Long> balanceList = groupAtm.getBalanceAmount();
        assertFalse(balanceList.isEmpty());
    }

    private void setEntities() {
        Cassette cassette1 = new Cassette(new Banknote(Nominal.RUB_50), 10);
        Cassette cassette2 = new Cassette(new Banknote(Nominal.RUB_200), 9);
        Cassette cassette3 = new Cassette(new Banknote(Nominal.RUB_2000), 8);

        Cassette cassette4 = new Cassette(new Banknote(Nominal.RUB_50), 7);
        Cassette cassette5 = new Cassette(new Banknote(Nominal.RUB_500), 5);
        Cassette cassette6 = new Cassette(new Banknote(Nominal.RUB_100), 6);

        cassetteListFirst.add(cassette1);
        cassetteListFirst.add(cassette2);
        cassetteListFirst.add(cassette3);

        cassetteListSecond.add(cassette4);
        cassetteListSecond.add(cassette5);
        cassetteListSecond.add(cassette6);

        cashMachine = new CashMachine(cassetteListFirst);
        cashMachine.setOnlineStatus(true);

        cashMachine2 = new CashMachine(cassetteListSecond);
        cashMachine2.setOnlineStatus(true);

        List<CashMachine> cashMachineList = new ArrayList<>();
        cashMachineList.add(cashMachine);
        cashMachineList.add(cashMachine2);

        groupAtm = new GroupAtm();
        groupAtm.setCashMachineList(cashMachineList);

    }
}