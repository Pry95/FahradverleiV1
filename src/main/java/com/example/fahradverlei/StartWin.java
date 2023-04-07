package com.example.fahradverlei;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartWin {

    public Stage stage;
    public Scene scene;

    public StartWin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("StartWin.fxml"));
        this.scene = new Scene(fxmlLoader.load(), 600, 400);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.show();
    }
}
