package com.example.proiectiss.repository.orm;

import com.example.proiectiss.model.Comanda;
import com.example.proiectiss.repository.interfaces.ComandaRepository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Properties;

public class ComandaOrmRepository implements ComandaRepository {
    private Dao<Comanda, Integer> ComandaDao;
    ConnectionSource connectionSource = null;

    public ComandaOrmRepository(Properties props) {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:identifier.sqlite");
            ComandaDao = DaoManager.createDao(connectionSource, Comanda.class);
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
    public Comanda add(Comanda entity) {
        try {
            ComandaDao.create(entity);
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
