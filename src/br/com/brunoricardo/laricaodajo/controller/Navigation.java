/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Navigation {

    public void navega(String tela) {
        try {
            Parent root;

            switch (tela) {
                case "TelaCadastroFuncionario":
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/pastelaofx/tela/TelaCadastroFuncionario.fxml"));
                    MainViewController.PRINCIPAL.getChildren().clear();
                    MainViewController.PRINCIPAL.getChildren().add(root);
                    break;
                case "TelaConfiguracoes":
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/pastelaofx/tela/TelaConfiguracoes.fxml"));
                    MainViewController.PRINCIPAL.getChildren().clear();
                    MainViewController.PRINCIPAL.getChildren().add(root);
                    break;
                case "TelaConfirmacaoPagamento":
                    root = FXMLLoader.load(getClass().getResource("/com/auinfo/pastelaofx/tela/checkout.fxml"));
                    MainViewController.PRINCIPAL.getChildren().clear();
                    MainViewController.PRINCIPAL.getChildren().add(root);
                    break;
                case "login":
                    root = FXMLLoader.load(getClass().getResource("/br/com/brunoricardo/laricaodajo/view/login.fxml"));
                    MainViewController.PRINCIPAL.getChildren().clear();
                    MainViewController.PRINCIPAL.getChildren().add(root);
                    break;
                case "sales":
                    root = FXMLLoader.load(getClass().getResource("/br/com/brunoricardo/laricaodajo/view/sales.fxml"));
                    MainViewController.PRINCIPAL.getChildren().clear();
                    MainViewController.PRINCIPAL.getChildren().add(root);
                    break;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
