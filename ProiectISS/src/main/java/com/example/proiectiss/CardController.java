package com.example.proiectiss;

import com.example.proiectiss.model.*;
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

public class CardController {
    Service service;
    User loggedUser;
    Comanda comanda;
    Stage stage;

    @FXML
    TextField numarTextField;
    @FXML
    TextField numeTextField;
    @FXML
    DatePicker expirarePicker;
    @FXML
    TextField cvvTextField;


    public void setService(Service s) {
        this.service = s;
    }

    public void setUser(User u) {
        this.loggedUser = u;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public static boolean isValidNumberFormat(String numar) {
        final boolean[] flag = {(numar.length() & 1) == 1};
        return Arrays.stream(
                        numar.split(""))
                .map(Integer::parseInt)
                .mapToInt(value -> value)
                .map(integer -> ((flag[0] ^= true) ? (integer * 2 - 1) % 9 + 1 : integer))
                .sum() % 10 == 0;
    }

    public static boolean isValidNameFormat(String nume) {
        String expression = "^[a-zA-Z\\s]+";
        return nume.matches(expression);
    }

    public static boolean isValidCVVFormat(String cvv) {
        String regex = "^[0-9]{3,4}$";
        Pattern p = Pattern.compile(regex);
        if (cvv == null) {
            return false;
        }
        Matcher m = p.matcher(cvv);
        return m.matches();
    }

    public static boolean isValidDateFormat(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    public boolean bankValidation(String numar, String nume, LocalDate expirare, String cvv) {
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

    public boolean validateFields(String numar, String nume, LocalDate expirare, String cvv) {
        String errors = "";
        if (!isValidNumberFormat(numar)) {
            errors += "Numar card invalid!\n";
        }
        if (!isValidNameFormat(nume)) {
            errors += "Nume invalid!\n";
        }
        if (!isValidDateFormat(expirare)) {
            errors += "Data de expirare invalida!\n";
        }
        if (!isValidCVVFormat(cvv)) {
            errors += "CVV invalid";
        }
        if (errors.equals("")) {
            return true;
        } else {
            displayInvalidMessage(errors);
            return false;
        }
    }

    public void verificaClicked(ActionEvent event) throws IOException {
        try {
            String numar = numarTextField.getText();
            String nume = numeTextField.getText();
            LocalDate expirare = expirarePicker.getValue();
            String cvv = cvvTextField.getText();

            if (validateFields(numar, nume, expirare, cvv)) {
                if (bankValidation(numar, nume, expirare, cvv)) {
                    Comanda c = service.addComanda(this.comanda);
                    displaySuccesMessage(event, c);
                } else displayInvalidMessage("Banca a refuzat tranzactia");
            }
        } catch (Exception ex) {
            displayInvalidMessage("Toate campurile trebuie completate!");
        }
    }
}
