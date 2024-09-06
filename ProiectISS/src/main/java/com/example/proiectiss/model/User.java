package com.example.proiectiss.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "utilizatori")
public class User implements Identifiable<Integer> {

    public enum UserType {
        ADMINISTRATOR, AGENT
    }

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "username")
    private String username;

    @DatabaseField(columnName = "password")
    private String parola;

    @DatabaseField(columnName = "userType")
    private UserType type;

    public User() {}

    public User(String username, String parola, UserType type) {
        this.username = username;
        this.parola = parola;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", type=" + type +
                '}';
    }
}
