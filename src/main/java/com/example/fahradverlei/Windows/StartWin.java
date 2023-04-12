package com.example.fahradverlei.Windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StartWin {

    public Stage stage;
    public Scene scene;

    public StartWin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartWin.class.getResource("/com/example/fahradverlei/StartWin.fxml"));
        this.scene = new Scene(fxmlLoader.load(), 828, 571);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.show();
    }
}
