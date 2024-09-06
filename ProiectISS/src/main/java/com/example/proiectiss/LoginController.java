package com.example.proiectiss;

import com.example.proiectiss.model.User;
import com.example.proiectiss.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    Service service;

    @FXML
    TextField usernameTextField;

    @FXML
    TextField parolaTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setService(Service service) {
        this.service = service;
    }


    private void showInvalidCredentialsAlert() {
        usernameTextField.setText("");
        parolaTextField.setText("");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid credentials");
        alert.setHeaderText("Invalid email or password!");
        alert.setContentText("Do you want to try again?");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    private void setAdministratorStage(ActionEvent event, FXMLLoader loader, User us, Service service) throws IOException {
        loader.setLocation(getClass().getResource("administrator.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        AdministratorController adminController = loader.getController();
        adminController.setUser(us);
        adminController.setService(service);
        stage.setTitle("Logged in as administrator");
    }

    private void setAgentStage(ActionEvent event, FXMLLoader loader, User us, Service service) throws IOException {
        loader.setLocation(getClass().getResource("agentVanzari.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        AgentController agentController = loader.getController();
        agentController.setUser(us);
        agentController.setService(service);
        stage.setTitle("Logged in as agent de vanzari");
    }

    public void LoginButtonClicked(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String parola = parolaTextField.getText();
        User user = service.login(username, parola);

        if (user == null) {
            showInvalidCredentialsAlert();
        } else {
            FXMLLoader loader = new FXMLLoader();

            if (user.getType() == User.UserType.ADMINISTRATOR) {
                setAdministratorStage(event, loader, user, service);
            } else if (user.getType() == User.UserType.AGENT){
                setAgentStage(event, loader, user, service);
            }
            stage.setWidth(700);
            stage.setHeight(600);
            stage.setResizable(false);

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

            stage.show();
        }
    }
}
