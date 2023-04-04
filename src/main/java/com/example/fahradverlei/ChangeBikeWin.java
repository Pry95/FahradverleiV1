package com.example.fahradverlei;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeBikeWin {

    public Stage stage;
    public Scene scene;
    public ChangeBikeWin.ChangeBikeWinController controller;
    public MainWin mainWin;

    public ChangeBikeWin(MainWin mainWin, Bike tempBike) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("ChangeBikeWin.fxml"));
        this.controller = new ChangeBikeWin.ChangeBikeWinController(mainWin, tempBike);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 1250, 807);
        this.stage = new Stage();
        this.stage.setTitle("Fahrraddaten ändern");
        this.stage.getIcons().add(new Image("file:src/Images/bike.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class ChangeBikeWinController{

        public MainWin mainWin;
        public Bike tempBike;

        public ChangeBikeWinController(MainWin mainWin, Bike tempBike) {
            this.mainWin = mainWin;
            this.tempBike = tempBike;
        }
    }
}
