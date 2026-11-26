package com.nosecrets;

import com.nosecrets.utilitites.MasterStageController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Sistema de Reservas");
        MasterStageController.getInstance(primaryStage).cambiarEscena("/scenes/Login.fxml");

    }

    public static void main(String[] args) {
        launch(args);
    }
}