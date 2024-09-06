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


public class AdministratorController {
    Service service;
    User loggedUser;
    Stage stage;

    @FXML
    TextField nameTextField;
    @FXML
    TextField priceTextField;
    @FXML
    TextField quantityTextField;

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


        tableViewProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameTextField.setText(getSelectedProduct().getName());
                priceTextField.setText(String.valueOf(getSelectedProduct().getPrice()));
                quantityTextField.setText(String.valueOf(getSelectedProduct().getQuantity()));
            }
        });
    }

    public void ConsultaButtonClicked(ActionEvent event) throws IOException {
        initModel();
    }

    public void addButtonClicked(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        try {
            double price = Double.parseDouble(priceTextField.getText());
            int quantity = Integer.parseInt(quantityTextField.getText());
            Product p = service.addProduct(new Product(name, price, quantity));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Adaugarea a fost efectuata cu succes!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            initModel();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid data");
            alert.setHeaderText("Error!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void deleteButtonClicked(ActionEvent event) throws IOException {
        try {
            Product p = service.deleteProduct(getSelectedProduct().getId());
            nameTextField.clear();
            quantityTextField.clear();
            priceTextField.clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Stergerea a fost efectuata cu succes!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            initModel();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid");
            alert.setHeaderText("Nu ati selectat niciun produs!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void updateButtonClicked(ActionEvent event) throws IOException {
        try {
            int id = getSelectedProduct().getId();
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            int quantity = Integer.parseInt(quantityTextField.getText());
            Product newProduct = new Product(name, price, quantity);
            newProduct.setId(id);
            Product p = service.updateProduct(newProduct);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Modificarea a fost efectuata cu succes!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            initModel();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid");
            alert.setHeaderText("Modificarea a esuat!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
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
