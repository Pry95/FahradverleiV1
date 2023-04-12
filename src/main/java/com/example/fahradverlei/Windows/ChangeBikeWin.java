package com.example.fahradverlei.Windows;

import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Bike;
import com.example.fahradverlei.ObjectStruktures.EBike;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChangeBikeWin {

    public Stage stage;
    public Scene scene;
    public ChangeBikeWin.ChangeBikeWinController controller;
    public MainWin mainWin;

    public ChangeBikeWin(MainWin mainWin, Bike tempBike) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("/com/example/fahradverlei/ChangeBikeWin.fxml"));
        this.controller = new ChangeBikeWin.ChangeBikeWinController(mainWin, tempBike,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Fahrraddaten ändern");
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
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
        public Label lblBatteryCap;
        public Label lblPerformance;
        public Label lblInfo;
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

            try{
                if (Objects.equals(comboBoxDesignType.getValue(), "EBike")){
                    Database.changeBikeDataFromDataBase(
                            Integer.parseInt(txtFieldID.getText()),
                            txtFieldName.getText(),
                            comboBoxFrameSize.getValue(),
                            comboBoxDesignType.getValue(),
                            Double.parseDouble(txtFieldPricePerDay.getText()),
                            comboBoxCondition.getValue(),
                            txtAreaConditionComment.getText(),
                            Integer.valueOf(txtFieldBatteryCap.getText()),
                            Integer.valueOf(txtFieldPerformance.getText()));
                }
                else{
                    Database.changeBikeDataFromDataBase(
                            Integer.parseInt(txtFieldID.getText()),
                            txtFieldName.getText(),
                            comboBoxFrameSize.getValue(),
                            comboBoxDesignType.getValue(),
                            Double.parseDouble(txtFieldPricePerDay.getText()),
                            comboBoxCondition.getValue(),
                            txtAreaConditionComment.getText(),
                            0,
                            0);
                }
                Database.readBikesFromDatabase();
                mainWin.controller.fillBikeTableView();
                changeBikeWin.stage.close();
            }
            catch (Exception e){
                lblInfo.setText("Falsche Eingabe!");
            }
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            if (!Objects.equals(tempBike.getDesignType(), "EBike")){
                txtFieldBatteryCap.setVisible(false);
                txtFieldPerformance.setVisible(false);
                lblBatteryCap.setVisible(false);
                lblPerformance.setVisible(false);

            }
            comboBoxDesignType.setOnAction(event -> {
                if(!Objects.equals(comboBoxDesignType.getSelectionModel().getSelectedItem(), "EBike")){
                    txtFieldBatteryCap.setText("");
                    txtFieldBatteryCap.setVisible(false);
                    txtFieldPerformance.setText("");
                    txtFieldPerformance.setVisible(false);
                    lblBatteryCap.setVisible(false);
                    lblPerformance.setVisible(false);
                }
                else {
                    try {
                        txtFieldBatteryCap.setVisible(true);
                        txtFieldPerformance.setVisible(true);
                        lblBatteryCap.setVisible(true);
                        lblPerformance.setVisible(true);
                        txtFieldBatteryCap.setText(String.valueOf(((EBike) tempBike).getBatteryCapacity()));
                        txtFieldPerformance.setText(String.valueOf(((EBike) tempBike).getPerformance()));

                    } catch (Exception e) {
                    }
                }

            });
            fillBikesCombobox();
            txtFieldID.setText(String.valueOf(tempBike.getID()));
            txtFieldName.setText(tempBike.getName());
            comboBoxFrameSize.setValue(Integer.parseInt(tempBike.getFrameSize()));
            comboBoxDesignType.setValue(tempBike.getDesignType());
            txtFieldPricePerDay.setText(String.valueOf(tempBike.getPricePerDay()));
            comboBoxCondition.setValue(tempBike.getCondition());
            txtAreaConditionComment.setText(tempBike.getConditionComment());

            try{
                txtFieldBatteryCap.setText(String.valueOf(((EBike)tempBike).getBatteryCapacity()));
                txtFieldPerformance.setText(String.valueOf(((EBike)tempBike).getPerformance()));
            }
            catch (Exception e){

            }

        }

        /**
         * Befüllt die Comboboxen mit den Werten für DesignType, Condition und FrameSize
         */
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
