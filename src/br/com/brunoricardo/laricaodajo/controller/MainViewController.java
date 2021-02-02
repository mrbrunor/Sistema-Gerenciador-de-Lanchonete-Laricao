/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.brunoricardo.laricaodajo.model.Box;
import br.com.brunoricardo.laricaodajo.model.Employee;
import br.com.brunoricardo.laricaodajo.model.Orderer;
import br.com.brunoricardo.laricaodajo.utility.Clock;
import br.com.brunoricardo.laricaodajo.utility.SetAnchors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class MainViewController implements Initializable {

    @FXML
    private AnchorPane principal;

    public static AnchorPane PRINCIPAL;
    
    public static Employee employee;
    
    public static Box box;

    public static Orderer orderer;

    public static boolean isPaid;
    
    public static Clock clock;
    
    public static SetAnchors setAnchors;
    
    public static final Navigation nav = new Navigation();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PRINCIPAL = principal;

        nav.navega("login");
    }

}
