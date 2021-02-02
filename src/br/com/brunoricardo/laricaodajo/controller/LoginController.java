package br.com.brunoricardo.laricaodajo.controller;

import br.com.brunoricardo.laricaodajo.dao.EmployeeDao;
import br.com.brunoricardo.laricaodajo.model.Employee;
import br.com.brunoricardo.laricaodajo.utility.Alerts;
import br.com.brunoricardo.laricaodajo.utility.Dialogues;
import br.com.brunoricardo.laricaodajo.utility.HexSha;
import br.com.brunoricardo.laricaodajo.utility.Validation;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    private Boolean result[] = {false, false};

    private final Validation validation = new Validation("-fx-border-color: rgb(181, 181, 181);",
            "-fx-border-color: white;",
            "-fx-background-color: white; -fx-border-color: rgb(181, 181, 181);",
            "-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%); -fx-border-color: white;");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btLogin.setOnAction(event -> login());
        btPassRecovery.setOnAction(event -> new Dialogues("CreateNewPasswordDialog"));
        tfUser.setOnAction(event -> pfPass.requestFocus());
        pfPass.setOnAction(event -> login());

        tfUser.setOnKeyReleased(event -> validation.validateText(tfUser, lbEUser, 3));
        pfPass.setOnKeyReleased(event -> validation.validateIndividual(pfPass, lbEPassword, 5));
    }


    void login() {

        if (validate()) {

            Employee employee = new Employee();
            EmployeeDao employeeDao = new EmployeeDao();
            employee.setUser(tfUser.getText());

            HexSha hexSha = new HexSha(String.valueOf(pfPass.getText()));
            employee.setPassword(hexSha.ConvertSha());

            employeeDao.openConnection();
            employee = employeeDao.findLogin(employee.getUser(), employee.getPassword());
            employeeDao.closeConnection();
            if (employee == null) {
                new Alerts("Efetuar Login", null, "Senha ou Usuário Invalidos").ErrorAlert();
            } else if (employee.getIsActive() == 1) {
                MainViewController.employee = employee;
                MainViewController.nav.navega("sales");
            } else {
                new Alerts("Efetuar Login", null, "Esta conta foi desativada pelo Administrador").WarningAlert();
            }
        }
    }

    private boolean validate() {
        result[0] = validation.validateText(tfUser, lbEUser, 3);
        result[1] = validation.validateIndividual(pfPass, lbEPassword, 5);
        return result[0] && result[1];
    }
}
