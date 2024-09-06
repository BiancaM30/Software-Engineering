package com.example.proiectiss.repository.interfaces;

import com.example.proiectiss.model.Product;
import com.example.proiectiss.model.User;

public interface UserRepository {
    public Iterable<User> findAll();
}
