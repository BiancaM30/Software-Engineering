package com.example.proiectiss;

import com.example.proiectiss.model.Comanda;
import com.example.proiectiss.model.User;
import com.example.proiectiss.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaypalController {
    Service service;
    User loggedUser;
    Comanda comanda;
    Stage stage;

    @FXML
    TextField emailTextField;
    @FXML
    TextField parolaTextField;


    public void setService(Service s) {
        this.service = s;
    }

    public void setUser(User u) {
        this.loggedUser = u;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public static boolean isValidEmailFormat(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }


    public boolean paypalSystemValidation(String email, String parola) {
        // banking validation system
        return true;
    }

    private void restoreAgentScene(ActionEvent event) {
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

    private void displaySuccesMessage(ActionEvent event, Comanda c) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(this.loggedUser.getUsername());
        alert.setHeaderText("Comanda cu id-ul " + c.getId() + " in valoare de " + c.getPretTotal() + " a fost plasata cu succes!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                restoreAgentScene(event);
            }
        });
    }

    private void displayInvalidMessage(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(mesaj);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void verificaClicked(ActionEvent event) throws IOException {
        try {
            String email = emailTextField.getText();
            String parola = parolaTextField.getText();
            if (isValidEmailFormat(email)) {
                if (paypalSystemValidation(email, parola)) {
                    Comanda c = service.addComanda(this.comanda);
                    displaySuccesMessage(event, c);
                } else displayInvalidMessage("Paypal a refuzat tranzactia");
            }
            else displayInvalidMessage("Format email invalid!");
        }
        catch (Exception ex){
            displayInvalidMessage("Toate campurile trebuie completate!");
        }
    }
}
