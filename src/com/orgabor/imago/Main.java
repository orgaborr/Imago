package com.orgabor.imago;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("imago.fxml"));
        primaryStage.setTitle("Imago");
        primaryStage.setScene(new Scene(root, 500, 375));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
