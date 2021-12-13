package ru.atm.models;

import ru.atm.exceptions.AtmException;

import java.util.*;

public class GroupAtm {

    private String activeAtm;
    private List<CashMachine> cashMachineList;
    private Map<String, List<CashMachine>> savePoint = new LinkedHashMap<>();

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

    public void addSavePoint() throws CloneNotSupportedException {
        String uuid = UUID.randomUUID().toString();
        List<CashMachine> listNew = new ArrayList<>();
        for (CashMachine cashMachine : cashMachineList) {
            List<Cassette> cassetteList = new ArrayList<>();
            for (Cassette cassette : cashMachine.getCassetteList()) {
                cassetteList.add(cassette.clone());
            }
            CashMachine cashMachineNew = new CashMachine(cassetteList);
            listNew.add(cashMachineNew);
        }

        this.setActiveAtm(uuid);
        this.setSavePoint(uuid, listNew);
    }

    public String rollbackSavePoint() {
        if (!savePoint.isEmpty() && savePoint.size() > 1) {
            List<CashMachine> prevPoint = new LinkedList<>();
            String activePoint = "";

            for (Map.Entry<String, List<CashMachine>> item : savePoint.entrySet()) {
                if (item.getKey().equals(activeAtm)) {
                    this.setCashMachineList(prevPoint);
                    this.setActiveAtm(activePoint);
                    return activePoint;
                } else {
                    prevPoint = item.getValue();
                    activePoint = item.getKey();
                }
            }
        }

        throw new AtmException("Rollback error, not found save points ", new Exception());
    }

    public ModeAtm changeMode() {
        if (!cashMachineList.isEmpty() && cashMachineList.stream().findFirst().get().isOnlineStatus()) {
            changeStatusOnlineAtm();
            return ModeAtm.OFFLINE;
        } else {
            changeStatusOnlineAtm();
            return ModeAtm.ONLINE;
        }
    }

    private void changeStatusOnlineAtm() {
        for (CashMachine cashMachine : cashMachineList) {
            if (cashMachine.isOnlineStatus()) {
                cashMachine.setOnlineStatus(false);
            } else {
                cashMachine.setOnlineStatus(true);
            }
        }
    }

}


