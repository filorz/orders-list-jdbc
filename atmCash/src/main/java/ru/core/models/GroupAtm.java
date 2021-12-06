package ru.core.models;

import java.util.*;

public class GroupAtm {

    private List<CashMachine> cashMachineList;
    private Map<String, List<CashMachine>> savePoint = new LinkedHashMap<>();
    private String activeAtm;

    public Map<String, List<CashMachine>> getSavePoint() {
        return savePoint;
    }

    public List<CashMachine> getSavePointByUuid(String uuid) {
        return savePoint.get(uuid);
    }

    public List<CashMachine> getCashMachineList() {
        return cashMachineList;
    }

    public void setCashMachineList(List<CashMachine> cashMachineList) {
        this.cashMachineList = cashMachineList;
    }

    public String getActiveSavePoint() {
        return activeAtm;
    }

    public void setActiveAtm(String activeAtm) {
        this.activeAtm = activeAtm;
    }

    public void setSavePoint(String uuid, List<CashMachine> cashMachineList) {
        this.savePoint.put(uuid, cashMachineList);
    }

}


