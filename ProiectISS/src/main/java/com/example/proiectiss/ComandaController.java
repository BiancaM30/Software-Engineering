package com.example.proiectiss;

import com.example.proiectiss.model.*;
import com.example.proiectiss.service.Service;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ComandaController {
    Service service;
    User loggedUser;
    Product produsComandat;
    int cantitateComandata;
    Adresa adresaComanda;
    TipPlata tipPlata;
    Stage stage;

    @FXML
    TextField taraTextField;
    @FXML
    TextField judetTextField;
    @FXML
    TextField localitateTextField;
    @FXML
    TextField stradaTextField;
    @FXML
    TextField numarTextField;
    @FXML
    TextArea observatiiTextArea;

    @FXML
    Label lblStatusAdresa;

    @FXML
    ComboBox comboBoxPlata;

    String plata[] = {"CASH", "CARD", "PAYPAL"};

    public void setService(Service s) {
        this.service = s;
    }

    public void setUser(User u) {
        this.loggedUser = u;
    }

    public void setComandaInfo(Product produs, int cantitate) {
        this.produsComandat = produs;
        this.cantitateComandata = cantitate;
    }

    public void initModel() {

    }

    @FXML
    public void initialize() {
        comboBoxPlata.setItems(FXCollections
                .observableArrayList(plata));

        comboBoxPlata.setOnAction((e) -> {
            this.tipPlata = TipPlata.valueOf(comboBoxPlata.getSelectionModel().getSelectedItem().toString());
        });
    }

    public void confirmaAdresaClicked(ActionEvent event) throws IOException {
        try {
            String tara = taraTextField.getText();
            String judet = judetTextField.getText();
            String localitate = localitateTextField.getText();
            String strada = stradaTextField.getText();
            int numar = Integer.parseInt(numarTextField.getText());
            String observatii = observatiiTextArea.getText();

            Adresa newAdresa = new Adresa(tara, judet, localitate, strada, numar, observatii);
            Adresa adresa = service.addAdresa(newAdresa);
            lblStatusAdresa.setTextFill(Color.web("#49be25"));
            lblStatusAdresa.setText("Adresa salvata");

            this.adresaComanda = adresa;

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    private void showComandaFinalizataMessage(ActionEvent event, Comanda c){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Comanda cu id-ul " + c.getId() + " in valoare de " + c.getPretTotal() + " a fost plasata cu succes!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                restoreAgentScene(event);
            }
        });
    }

    public void showCardStage(ActionEvent event, Comanda newComanda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("plata_card.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        CardController cardController = loader.getController();
        cardController.setService(service);
        cardController.setUser(loggedUser);
        cardController.setComanda(newComanda);

        stage.setHeight(376);
        stage.setWidth(409);
        stage.setResizable(false);
        stage.show();
    }

    public void showPaypalStage(ActionEvent event, Comanda newComanda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("plata_paypal.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        PaypalController paypalController = loader.getController();
        paypalController.setService(service);
        paypalController.setUser(loggedUser);
        paypalController.setComanda(newComanda);

        stage.setHeight(400);
        stage.setWidth(323);
        stage.setResizable(false);
        stage.show();
    }

    public void finalizareClicked(ActionEvent event) throws IOException {
        Comanda newComanda = new Comanda(this.loggedUser, this.produsComandat, this.cantitateComandata,
                this.adresaComanda, this.tipPlata);

        if (this.tipPlata == TipPlata.CASH) {
            Comanda c = service.addComanda(newComanda);
            showComandaFinalizataMessage(event, c);
        }
        else if (this.tipPlata == TipPlata.CARD){
          showCardStage(event, newComanda);
        }
        else if (this.tipPlata == TipPlata.PAYPAL){
            showPaypalStage(event, newComanda);
        }
    }

    private void restoreAgentScene(ActionEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("agentVanzari.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AgentController comandaController = loader.getController();
        comandaController.setService(service);
        comandaController.setUser(loggedUser);

        stage.setHeight(600);
        stage.setWidth(600);
        stage.setResizable(false);
        stage.show();
    }


}
