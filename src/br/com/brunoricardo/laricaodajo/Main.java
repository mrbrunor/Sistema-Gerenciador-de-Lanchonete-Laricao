package br.com.brunoricardo.laricaodajo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/brunoricardo/laricaodajo/view/login.fxml"));
        primaryStage.setTitle("Laricão da Jô");
        primaryStage.setScene(new Scene(root, 1004, 680));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
