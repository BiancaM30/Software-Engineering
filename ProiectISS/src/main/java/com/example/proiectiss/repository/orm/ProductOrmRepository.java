package com.example.proiectiss.repository.orm;

import com.example.proiectiss.model.Product;
import com.example.proiectiss.repository.JdbcUtils;
import com.example.proiectiss.repository.RepositoryException;
import com.example.proiectiss.repository.interfaces.ProductRepository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductOrmRepository implements ProductRepository {
    private Dao<Product, Integer> carteDao;
    ConnectionSource connectionSource = null;

    public ProductOrmRepository(Properties porps) {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:identifier.sqlite");
            carteDao = DaoManager.createDao(connectionSource, Product.class);
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
    public Product findById(Integer integer) {
        try {
            return carteDao.queryForId(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Product> findAll() {
        try {
            return carteDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product add(Product entity) {
        try {
            carteDao.create(entity);
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product delete(Integer integer) {
        try {
            carteDao.deleteById(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product update(Product entity) {
        try {
            carteDao.update(entity);
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
