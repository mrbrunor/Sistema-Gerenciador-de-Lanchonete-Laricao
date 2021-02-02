package br.com.brunoricardo.laricaodajo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateNewPasswordDialogController implements Initializable{

    @FXML
    private TextField tfUser;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    @FXML
    private Button btCancel;

    @FXML
    private Button btConfirm;

    @FXML
    private Label lbEUser;

    @FXML
    private Label lbEPassword;

    @FXML
    private Label lbEConfirmPassword;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Ola");
    }

}
