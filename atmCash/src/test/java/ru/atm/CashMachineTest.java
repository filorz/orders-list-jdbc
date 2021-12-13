package ru.atm;


import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;
import ru.atm.models.CashMachine;
import ru.atm.models.Cassette;
import ru.atm.models.Nominal;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CashMachineTest {

    public static final int QUERY_SUM = 600;
    private CashMachine cashMachine;
    private List<Cassette> cassetteListFirst = new ArrayList<>();

    @BeforeEach
    void setUp(){
        setEntities();
    }

    @Test
    public void testMonitorWorksGreat() {
        assertNotNull(cassetteListFirst);
        assertNotNull(cashMachine);

        long balance = cashMachine.getBalanceAmount() - QUERY_SUM;
        cashMachine.extraditionBySum(QUERY_SUM);
        assertEquals(cashMachine.getBalanceAmount(), balance);

    }

    private void setEntities() {
        Cassette cassette1 = new Cassette(Nominal.RUB_50, 10);
        Cassette cassette2 = new Cassette(Nominal.RUB_200, 9);
        Cassette cassette3 = new Cassette(Nominal.RUB_2000, 8);

        cassetteListFirst.add(cassette1);
        cassetteListFirst.add(cassette2);
        cassetteListFirst.add(cassette3);

        cashMachine = new CashMachine(cassetteListFirst);
        cashMachine.setOnlineStatus(true);
    }
}
