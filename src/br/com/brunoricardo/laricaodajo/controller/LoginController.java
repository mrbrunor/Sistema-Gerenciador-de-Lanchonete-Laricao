package br.com.brunoricardo.laricaodajo.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {

    @FXML
    private TextField tfUser;

    @FXML
    private PasswordField pfPass;

    @FXML
    private Button btPassRecovery;

    @FXML
    private Button btLogin;

    @FXML
    private Label lbEUser;

    @FXML
    private Label lbEPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Olá");
    }
}
