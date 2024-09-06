package com.example.proiectiss.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "comenzi")
public class Comanda implements Identifiable<Integer> {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "idAgent", foreign = true)
    private User agent;

    @DatabaseField(columnName = "idProdus", foreign = true)
    private Product produs;

    @DatabaseField(columnName = "cantitate")
    private int cantitateProdus;

    @DatabaseField(columnName = "idAdresa", foreign = true)
    private Adresa adresa;

    @DatabaseField(columnName = "tipPlata")
    TipPlata tipPlata;

    @DatabaseField(columnName = "pret")
    private double pretTotal;

    final int deliveryFee = 15;

    public Comanda() {
    }

    public Comanda(User agent, Product produs, int cantitateProdus, Adresa adresa, TipPlata tipPlata) {
        this.agent = agent;
        this.produs = produs;
        this.cantitateProdus = cantitateProdus;
        this.adresa = adresa;
        this.tipPlata = tipPlata;
        this.pretTotal = this.computeTotalPrice();
    }


    private double computeTotalPrice() {
        double value = this.produs.getPrice() * this.cantitateProdus + deliveryFee;
        return Math.floor(value * 100) / 100;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public Product getProdus() {
        return produs;
    }

    public void setProdus(Product produs) {
        this.produs = produs;
    }

    public int getCantitateProdus() {
        return cantitateProdus;
    }

    public void setCantitateProdus(int cantitateProdus) {
        this.cantitateProdus = cantitateProdus;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public TipPlata getTipPlata() {
        return tipPlata;
    }

    public void setTipPlata(TipPlata tipPlata) {
        this.tipPlata = tipPlata;
    }

    public double getPretTotal() {
        return pretTotal;
    }

    public void setPretTotal(double pretTotal) {
        this.pretTotal = pretTotal;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
