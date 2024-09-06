package com.example.proiectiss;

import com.example.proiectiss.model.Product;
import com.example.proiectiss.model.User;
import com.example.proiectiss.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AgentController {
    Service service;
    User loggedUser;
    final int deliveryFee = 15;
    Stage stage;

    @FXML
    TextField nameTextField;
    @FXML
    TextField priceTextField;
    @FXML
    TextField quantityTextField;

    @FXML
    Label lblDelivery;
    @FXML
    Label lblTotalPrice;

    @FXML
    TableColumn<Product, String> tableColumnName;
    @FXML
    TableColumn<Product, Double> tableColumnPrice;
    @FXML
    TableColumn<Product, Integer> tableColumnQuantity;
    @FXML
    TableView<Product> tableViewProducts;

    ObservableList<Product> modelProducts = FXCollections.observableArrayList();

    public void setService(Service s) {
        this.service = s;
    }

    public void setUser(User u) {
        this.loggedUser = u;
    }

    public void initModel() {
        List<Product> allProducts = service.findAllProducts();
        modelProducts.setAll(allProducts);
    }

    private Product getSelectedProduct() {

        return tableViewProducts.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tableColumnQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        tableViewProducts.setItems(modelProducts);

        lblDelivery.setText(String.valueOf(deliveryFee + " lei"));
        tableViewProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameTextField.setText(getSelectedProduct().getName());
                priceTextField.setText(String.valueOf(getSelectedProduct().getPrice()));

                if (getSelectedProduct().getQuantity() >= 1) {
                    quantityTextField.setText("1");
                    double price = Double.parseDouble(priceTextField.getText());
                    lblTotalPrice.setText(String.valueOf(price + deliveryFee));
                    quantityTextField.setDisable(false);
                } else {
                    quantityTextField.setDisable(true);
                }
            }
        });

        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (Integer.parseInt(quantityTextField.getText()) <= getSelectedProduct().getQuantity()) {
                    double price = Integer.parseInt(quantityTextField.getText()) * Double.parseDouble(priceTextField.getText());
                    lblTotalPrice.setText(String.valueOf(price + deliveryFee));
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Logout");
                    alert.setHeaderText("Stoc insuficient");
                    alert.setContentText("Puteti comanda maxim " + getSelectedProduct().getQuantity() + " produse");
                    alert.show();
                    quantityTextField.setText(String.valueOf(getSelectedProduct().getQuantity()));
                }
            } catch (Exception ex) {
            }
        });
    }

    public void ConsultaButtonClicked(ActionEvent event) throws IOException {
        initModel();
    }

    public void comandaButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("comanda.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        ComandaController comandaController = loader.getController();
        comandaController.setService(service);
        comandaController.setUser(loggedUser);
        comandaController.setComandaInfo(getSelectedProduct(), Integer.valueOf(quantityTextField.getText()));

        stage.setHeight(600);
        stage.setWidth(400);
        stage.setResizable(false);
        stage.show();
    }


    public void logoutButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to continue?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));

            LoginController loginController = loader.getController();
            loginController.setService(service);
            stage.setHeight(407);
            stage.setWidth(328);
            stage.setResizable(false);
            stage.show();
        }
    }


}
