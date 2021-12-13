package ru.core.models;

public class Cassette implements Cloneable {

    private Nominal nominal;
    private int count;

    public Cassette(Nominal nominal, int count) {
        this.nominal = nominal;
        this.count = count;
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

    @Override
    public Cassette clone() throws CloneNotSupportedException {
        Cassette cassette = (Cassette) super.clone();
        cassette.setCount(this.count);
        cassette.setNominal(this.nominal);

        return cassette;
    }
}