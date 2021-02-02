/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.utility;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class Alerts {

    private Alert alert;
    private final String content;
    private final String header;
    private final String title;

    public Alerts(String title, String header, String content) {
        this.title = title;
        this.header = header;
        this.content = content;
    }

    public void InformationAlert() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        fillAlert();
        alert.showAndWait();
    }

    public void ErrorAlert() {
        alert = new Alert(Alert.AlertType.ERROR);
        fillAlert();
        alert.showAndWait();
    }

    public void WarningAlert() {
        alert = new Alert(Alert.AlertType.WARNING);
        fillAlert();
        alert.showAndWait();
    }

    public Optional<ButtonType> ConfirmationAlert() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        fillAlert();
        return alert.showAndWait();
    }

    public String InputAlert() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        Optional<String> quantidade = dialog.showAndWait();

        if (quantidade.isPresent()) {
            return quantidade.get();
        } else {
            return null;
        }
    }

    private void fillAlert() {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
    }
}
