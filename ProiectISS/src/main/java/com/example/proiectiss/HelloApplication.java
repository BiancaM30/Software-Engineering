package com.example.proiectiss;

import com.example.proiectiss.repository.interfaces.AdresaRepository;
import com.example.proiectiss.repository.interfaces.ComandaRepository;
import com.example.proiectiss.repository.interfaces.ProductRepository;
import com.example.proiectiss.repository.interfaces.UserRepository;
import com.example.proiectiss.repository.orm.AdresaOrmRepository;
import com.example.proiectiss.repository.orm.ComandaOrmRepository;
import com.example.proiectiss.repository.orm.ProductOrmRepository;
import com.example.proiectiss.repository.orm.UserOrmRepository;
import com.example.proiectiss.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties serverProps=new Properties();

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        UserRepository userRepo = new UserOrmRepository(serverProps);
        ProductRepository productRepo = new ProductOrmRepository(serverProps);
        AdresaRepository adresaRepo = new AdresaOrmRepository(serverProps);
        ComandaRepository comandaRepo = new ComandaOrmRepository(serverProps);

        Service service = new Service(userRepo, productRepo, adresaRepo, comandaRepo);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        stage.setHeight(407);
        stage.setWidth(328);
        stage.setResizable(false);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}