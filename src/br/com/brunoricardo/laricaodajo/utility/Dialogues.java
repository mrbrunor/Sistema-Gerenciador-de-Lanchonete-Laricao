/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.utility;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialogues {

    public Dialogues(String dialogue) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/com/brunoricardo/laricaodajo/view/" + dialogue + ".fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            //Logger.getLogger(Dialogues.class).debug(e);
        }
    }
}
