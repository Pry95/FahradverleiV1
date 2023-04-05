package com.example.fahradverlei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        MainWin mainWin = new MainWin();
        WorkingHourWin test = new WorkingHourWin(mainWin,null);
    }

    public static void main(String[] args) {
        launch();
    }
}