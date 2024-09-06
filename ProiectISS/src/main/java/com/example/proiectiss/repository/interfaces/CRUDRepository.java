package com.example.proiectiss.repository.interfaces;


public interface CRUDRepository<T, Tid> {
    T add(T elem);
    T delete(Tid id);
    T update(T elem);
    T findById(Tid id);
    Iterable<T> findAll();
}
