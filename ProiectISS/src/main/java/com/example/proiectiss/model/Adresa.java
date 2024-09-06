package com.example.proiectiss.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "adrese")
public class Adresa implements Identifiable<Integer>{
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "tara")
    private String tara;

    @DatabaseField(columnName = "judet")
    private String judet;

    @DatabaseField(columnName = "localitate")
    private String localitate;

    @DatabaseField(columnName = "strada")
    private String strada;

    @DatabaseField(columnName = "numar")
    private int numar;

    @DatabaseField(columnName = "observatii")
    private String observatii;

    public Adresa() {}

    public Adresa(String tara, String judet, String localitate, String strada, int numar, String observatii) {
        this.tara = tara;
        this.judet = judet;
        this.localitate = localitate;
        this.strada = strada;
        this.numar = numar;
        this.observatii = observatii;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
