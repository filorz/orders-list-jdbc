package ru.atm.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.atm.exceptions.AtmException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashMachineTest {

    public static final int QUERY_SUM = 650;
    public static final int ACTUAL_COUNT_BANKNOTE_200 = 6;
    public static final int ACTUAL_COUNT_BANKNOTE_50 = 9;
    private CashMachine cashMachine;
    private List<Cassette> cassetteListFirst = new ArrayList<>();

    @BeforeEach
    void setUp() {
        setEntities();
    }

    @Test
    void extraditionBySum_changeCassetteExtradition() {
        List<Banknote> banknotesList = new ArrayList<>();
        List<Cassette> changeCassettes = new ArrayList<>();

        extraditionBanknoteAtm(banknotesList, changeCassettes);

        assertNotNull(changeCassettes);
        assertNotNull(banknotesList);

        assertEquals(changeCassettes.get(0).getBanknote().getNominal(), Nominal.RUB_200);
        assertEquals(changeCassettes.get(1).getBanknote().getNominal(), Nominal.RUB_50);

        assertEquals(changeCassettes.get(0).getCount(), ACTUAL_COUNT_BANKNOTE_200);
        assertEquals(changeCassettes.get(1).getCount(), ACTUAL_COUNT_BANKNOTE_50);

    }

    @Test
    void extraditionBySum_changeBalance() {
        long totalSumExtradition;
        long oldBalance = cashMachine.getBalanceAmount();
        totalSumExtradition = extraditionBanknoteAtm(new ArrayList<>(), new ArrayList<>());

        assertEquals(oldBalance - QUERY_SUM, cashMachine.getBalanceAmount());
        assertEquals(totalSumExtradition, QUERY_SUM);
    }

    @Test
    void extraditionBySum_returnThrow() {
        cashMachine.setCassetteList(null);
        assertThrows(AtmException.class, () -> cashMachine.extraditionBySum(QUERY_SUM));
    }

    @Test
    void onlineStatusAtm_changeStatusAtm() {
        cashMachine.setOnlineStatus(false);
        assertNotEquals(cashMachine.isOnlineStatus(), true);
    }

    private long extraditionBanknoteAtm(List<Banknote> banknoteList, List<Cassette> changeCassettes) {
        long totalSumExtradition = 0;
        banknoteList = cashMachine.extraditionBySum(QUERY_SUM);
        for (Banknote banknote : banknoteList) {
            totalSumExtradition += banknote.getNominal().getType();
        }
        for (Cassette cassette : cashMachine.getCassetteList()) {
            for (Banknote banknote : banknoteList) {
                if (!changeCassettes.contains(cassette)
                        && banknote.getNominal().equals(cassette.getBanknote().getNominal())) {
                    changeCassettes.add(cassette);
                }
            }
        }

        return totalSumExtradition;
    }

    private void setEntities() {
        Cassette cassette1 = new Cassette(new Banknote(Nominal.RUB_50), 10);
        Cassette cassette2 = new Cassette(new Banknote(Nominal.RUB_200), 9);
        Cassette cassette3 = new Cassette(new Banknote(Nominal.RUB_2000), 8);

        cassetteListFirst.add(cassette1);
        cassetteListFirst.add(cassette2);
        cassetteListFirst.add(cassette3);

        cashMachine = new CashMachine(cassetteListFirst);
        cashMachine.setOnlineStatus(true);
    }
}
