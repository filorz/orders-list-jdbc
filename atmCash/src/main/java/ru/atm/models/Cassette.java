package ru.atm.models;

public class Cassette implements Cloneable {

    private Banknote banknote;
    private int count;

    public Cassette(Banknote banknote, int count) {
        this.banknote = banknote;
        this.count = count;
    }

    public Banknote getBanknote() {
        return banknote;
    }

    public void setBanknote(Banknote banknote) {
        this.banknote = banknote;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public Cassette clone() throws CloneNotSupportedException {
        Cassette cassette = (Cassette) super.clone();
        cassette.setCount(this.count);
        cassette.setBanknote(this.banknote);

        return cassette;
    }
}