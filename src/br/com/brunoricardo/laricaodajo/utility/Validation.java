/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.utility;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Validation {

    /* Exemplo
    fundoErro = "-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);";
    fundoNormal = "-fx-background-color: white; -fx-border-color: rgb(181, 181, 181);";
    bordaErro = "-fx-border-color: red;";
    bordaNormal = "-fx-border-color: rgb(181, 181, 181);";
    */

    public String fundoErro;
    public String fundoNormal;
    public String bordaErro;
    public String bordaNormal;

    public Validation(String bordaNormal, String bordaErro, String fundoNormal, String fundoErro) {
        this.fundoErro = fundoErro;
        this.fundoNormal = fundoNormal;
        this.bordaErro = bordaErro;
        this.bordaNormal = bordaNormal;
    }

    public boolean validateText(TextField tf, Label lb, int i) {
        if (tf.getText().isEmpty() || tf.getText().length() < i) {
            tf.setStyle(fundoErro);
            tf.setPromptText("");
            lb.setVisible(true);
            return false;
        } else {
            tf.setStyle(fundoNormal);
            lb.setVisible(false);
            return true;
        }
    }

    public boolean validateNumber(TextField tf, Label lb, int i) {
        if (tf.getText().isEmpty() || !tf.getText().matches("\\d+") || tf.getText().length() < i) {
            tf.setStyle(fundoErro);
            tf.setPromptText("");
            lb.setVisible(true);
            return false;
        } else {
            tf.setStyle(fundoNormal);
            lb.setVisible(false);
            return true;
        }
    }

    public boolean validateNumberDouble(TextField tf, Label lb, int i) {
        if (tf.getText().isEmpty() || !tf.getText().matches("\\d\\.\\d") || tf.getText().length() < i) {
            tf.setStyle(fundoErro);
            tf.setPromptText("");
            lb.setVisible(true);
            return false;
        } else {
            tf.setStyle(fundoNormal);
            lb.setVisible(false);
            return true;
        }
    }

    public boolean validateCb(ComboBox cb) {
        if (cb.getSelectionModel().getSelectedIndex() != -1) {
            cb.setStyle(bordaNormal);
            return true;
        } else {
            cb.setStyle(bordaErro);
            return false;
        }
    }

    public boolean validaData(DatePicker dp) {
        if (dp.getValue() == null) {
            dp.setStyle(fundoErro);
            return false;
        } else {
            dp.setStyle(fundoNormal);
            return true;
        }
    }

    public boolean validateIndividual(PasswordField pf, Label lb, int i) {
        if (pf.getText().length() < i) {
            pf.setStyle(fundoErro);
            pf.setPromptText("");
            lb.setVisible(true);
            return false;
        } else {
            pf.setStyle(fundoNormal);
            lb.setVisible(false);
            return true;
        }
    }

    public boolean validaPfConfirmacao(PasswordField pf, PasswordField pfConfirmacao, Label lb, int i) {
        if (pfConfirmacao.getText().length() < i || !pf.getText().equals(pfConfirmacao.getText())) {
            pfConfirmacao.setStyle(fundoErro);
            pfConfirmacao.setPromptText("");
            lb.setVisible(true);
            return false;
        } else {
            pfConfirmacao.setStyle(fundoNormal);
            lb.setVisible(false);
            return true;
        }
    }

    public boolean validaCPF(TextField tf, Label lb) {
        if (CNP.isValidCPF(tf.getText())) {
            tf.setStyle(fundoNormal);
            lb.setVisible(false);
            return true;
        } else {
            tf.setStyle(fundoErro);
            tf.setPromptText("");
            lb.setVisible(true);
            return false;
        }
    }

}
