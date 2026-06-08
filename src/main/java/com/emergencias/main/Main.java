package com.emergencias.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vista_emergencia.fxml"));
        primaryStage.setTitle("EMERLINK - Emergencias Sanitarias");
        // Ajustamos dimensiones exactas para emular un dispositivo móvil
        primaryStage.setScene(new Scene(root, 380, 650));
        primaryStage.setResizable(false); // Fija el tamaño del simulador móvil
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}