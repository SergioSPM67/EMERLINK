package com.emergencias.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Modificamos la ruta para que busque el archivo FXML en el mismo paquete
        Parent root = FXMLLoader.load(getClass().getResource("vista_emergencia.fxml"));
        primaryStage.setTitle("SISTEMA EMERLINK - SERGIO P.M.");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}