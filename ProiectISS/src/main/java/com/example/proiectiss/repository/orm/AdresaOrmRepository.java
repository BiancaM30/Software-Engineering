package com.example.proiectiss.repository.orm;


import com.example.proiectiss.model.Adresa;
import com.example.proiectiss.repository.interfaces.AdresaRepository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Properties;

public class AdresaOrmRepository implements AdresaRepository {
    private Dao<Adresa, Integer> adresaDao;
    ConnectionSource connectionSource = null;

    public AdresaOrmRepository(Properties porps) {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:identifier.sqlite");
            adresaDao = DaoManager.createDao(connectionSource, Adresa.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Adresa add(Adresa entity) {
        try {
            adresaDao.create(entity);
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

