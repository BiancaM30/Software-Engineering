package com.example.proiectiss.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "produse")
public class Product implements Identifiable<Integer> {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "nume")
    private String name;

    @DatabaseField(columnName = "pret")
    private double price;

    @DatabaseField(columnName = "cantitate")
    private int quantity;

    public Product() {}

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
