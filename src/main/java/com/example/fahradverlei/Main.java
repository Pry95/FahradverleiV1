package com.example.fahradverlei;


import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        StartWin startWin = new StartWin();
        MainWin mainWin = new MainWin();
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            mainWin.stage.show();
            startWin.stage.close();
        });
        delay.play();
    }

    public static void main(String[] args) {
        launch();
    }
}