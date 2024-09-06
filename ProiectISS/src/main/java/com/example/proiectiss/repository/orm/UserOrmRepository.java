package com.example.proiectiss.repository.orm;


import com.example.proiectiss.model.Product;
import com.example.proiectiss.model.User;
import com.example.proiectiss.repository.interfaces.ProductRepository;
import com.example.proiectiss.repository.interfaces.UserRepository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Properties;

public class UserOrmRepository implements UserRepository {
    private Dao<User, Integer> userDao;
    ConnectionSource connectionSource = null;

    public UserOrmRepository(Properties props) {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:identifier.sqlite");
            userDao = DaoManager.createDao(connectionSource, User.class);
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
        }}

    @Override
    public Iterable<User> findAll() {
        try {
            return userDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
