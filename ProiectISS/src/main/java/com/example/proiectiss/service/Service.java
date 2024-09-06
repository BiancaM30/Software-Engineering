package com.example.proiectiss.service;

import com.example.proiectiss.model.Adresa;
import com.example.proiectiss.model.Comanda;
import com.example.proiectiss.model.Product;
import com.example.proiectiss.model.User;
import com.example.proiectiss.repository.interfaces.AdresaRepository;
import com.example.proiectiss.repository.interfaces.ComandaRepository;
import com.example.proiectiss.repository.interfaces.ProductRepository;
import com.example.proiectiss.repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private UserRepository userRepo;
    private ProductRepository productRepo;
    private AdresaRepository adresaRepo;
    private ComandaRepository comandaRepo;

    public Service(UserRepository userRepo, ProductRepository productRepo, AdresaRepository adresaRepo, ComandaRepository comandaRepo) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.adresaRepo = adresaRepo;
        this.comandaRepo=comandaRepo;
    }

    public User login(String username, String parola) {
        Iterable<User> itrUsers = userRepo.findAll();
        List<User> users = new ArrayList<>();
        itrUsers.forEach(users::add);
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getParola().equals(parola))
                return user;
        }
        return null;
    }

    public Product addProduct(Product product) {
        return productRepo.add(product);
    }

    public Product deleteProduct(Integer id) {
        return productRepo.delete(id);
    }

    public Product updateProduct(Product product) {
        return productRepo.update(product);
    }

    public Adresa addAdresa(Adresa adresa) {
        return adresaRepo.add(adresa);
    }

    public Comanda addComanda(Comanda comanda) {
        Product produs = comanda.getProdus();
        int cantitateComandata = comanda.getCantitateProdus();
        Product modifiedProduct = produs;
        modifiedProduct.setQuantity(produs.getQuantity()-cantitateComandata);
        productRepo.update(modifiedProduct);
        return comandaRepo.add(comanda);
    }

    public List<Product> findAllProducts() {
        Iterable<Product> itrEvents = productRepo.findAll();
        List<Product> products = new ArrayList<>();
        itrEvents.forEach(products::add);
        return products;
    }

}
