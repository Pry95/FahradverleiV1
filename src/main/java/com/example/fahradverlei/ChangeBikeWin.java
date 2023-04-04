package com.example.fahradverlei;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChangeBikeWin {

    public Stage stage;
    public Scene scene;
    public ChangeBikeWin.ChangeBikeWinController controller;
    public MainWin mainWin;

    public ChangeBikeWin(MainWin mainWin, Bike tempBike) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("ChangeBikeWin.fxml"));
        this.controller = new ChangeBikeWin.ChangeBikeWinController(mainWin, tempBike,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Fahrraddaten ändern");
        this.stage.getIcons().add(new Image("file:src/Images/bike.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class ChangeBikeWinController implements Initializable {

        public MainWin mainWin;
        public Bike tempBike;
        public ChangeBikeWin changeBikeWin;

        public Button btnBack;
        public Button btnSave;

        public TextField txtFieldID;
        public TextField txtFieldName;
        public TextField txtFieldPricePerDay;
        public TextField txtFieldBatteryCap;
        public TextField txtFieldPerformance;
        public TextArea txtAreaConditionComment;
        public ComboBox<Integer> comboBoxFrameSize;
        public ComboBox<String> comboBoxDesignType;
        public ComboBox<String> comboBoxCondition;


        // Konstruktor von ChangeBikeWinController
        public ChangeBikeWinController(MainWin mainWin, Bike tempBike, ChangeBikeWin changeBikeWin) {
            this.changeBikeWin = changeBikeWin;
            this.mainWin = mainWin;
            this.tempBike = tempBike;
        }

        @FXML
        public void btnBack(){
            changeBikeWin.stage.close();
        }
        @FXML
        public void btnSave(){

        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            txtFieldID.setText(String.valueOf(tempBike.getID()));
            txtFieldName.setText(tempBike.getName());
            comboBoxFrameSize.setPromptText(String.valueOf(tempBike.getFrameSize()));
            comboBoxDesignType.setPromptText(tempBike.getDesignType());
            txtFieldPricePerDay.setText(String.valueOf(tempBike.getPricePerDay()));
            comboBoxCondition.setPromptText(tempBike.getCondition());
            txtAreaConditionComment.setText(tempBike.getConditionComment());
            fillBikesCombobox();
            try{
                txtFieldBatteryCap.setText(String.valueOf(((EBike)tempBike).getBatteryCapacity()));
                txtFieldPerformance.setText(String.valueOf(((EBike)tempBike).getPerformance()));
            }
            catch (Exception e){

            }
        }

        public void fillBikesCombobox(){
            List<Integer> frameSizeList = new ArrayList<>();
            for(int i = 14;i <= 64;i++){
                frameSizeList.add(i);
            }
            comboBoxDesignType.getItems().addAll("Rennrad", "Citybike", "Bmx", "EBike", "MountainBike");
            comboBoxCondition.getItems().addAll("Sehr Gut", "Gut", "Schlecht", "Sehr Schlecht");
            comboBoxFrameSize.getItems().addAll(frameSizeList);

        }
    }
}
