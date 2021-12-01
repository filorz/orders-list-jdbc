package ru.core.models;

public class Cassette {

    private Nominal nominal;
    private int count;
    private boolean statusExtradition;

    public Cassette(Nominal nominal, int count, boolean statusExtradition) {
        this.nominal = nominal;
        this.count = count;
        this.statusExtradition = statusExtradition;
    }

    public boolean isStatusExtradition() {
        return statusExtradition;
    }

    public void setStatusExtradition(boolean statusExtradition) {
        this.statusExtradition = statusExtradition;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public void setNominal(Nominal nominal) {
        this.nominal = nominal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
