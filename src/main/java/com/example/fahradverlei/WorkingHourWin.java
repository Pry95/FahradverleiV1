package com.example.fahradverlei;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorkingHourWin {

    public Stage stage;
    public Scene scene;
    public WorkingHourController controller;
    public MainWin mainWin;

    public WorkingHourWin(MainWin mainWin, Employee tempEmployee) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("WorkingHourWin.fxml"));
        this.controller = new WorkingHourController(mainWin, tempEmployee, this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Arbeitszeiten verwalten");
        this.stage.getIcons().add(new Image("file:src/Images/bike.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class WorkingHourController implements Initializable {

        public MainWin mainWin;
        public Employee tempEmployee;
        public WorkingHourWin workingHourWin;
        public ComboBox<LocalTime> comboStart;
        public ComboBox<LocalTime> comboEnd;
        public ComboBox<LocalTime> comboBreakStart;
        public ComboBox<LocalTime> comboBreakEnd;
        public DatePicker datepicker;
        public Label lblEmployee;
        public Label lblInfo;
        public TableView<WorkingHours> tableViewWorkingHour;
        public TableColumn<WorkingHours,LocalDate> ColumnDate;
        public TableColumn<WorkingHours,LocalTime> ColumnStart;
        public TableColumn<WorkingHours,LocalTime> ColumnBreakStart;
        public TableColumn<WorkingHours,LocalTime> ColumnBreakEnd;
        public TableColumn<WorkingHours,LocalTime> ColumnEnd;
        public TableColumn<WorkingHours,Double> ColumnHours;



        public WorkingHourController(MainWin mainWin, Employee tempEmployee, WorkingHourWin workingHourWin) {
            this.mainWin = mainWin;
            this.tempEmployee = tempEmployee;
            this.workingHourWin = workingHourWin;
        }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            fillComboBoxesTime();

        }

        public void fillComboBoxesTime(){
            List<LocalTime> times = new ArrayList<>();
            LocalTime time = LocalTime.of(6,0);
            while (!time.equals(LocalTime.of(22,5))) {
                times.add(time);
                time = time.plusMinutes(5);
            }

            comboStart.getItems().addAll(times);
            comboStart.setValue(LocalTime.of(7,0));
            comboEnd.getItems().addAll(times);
            comboEnd.setValue(LocalTime.of(15,0));
            comboBreakEnd.getItems().addAll(times);
            comboBreakStart.getItems().addAll(times);
            datepicker.setValue(LocalDate.now());

        }
        @FXML
        public void btnSave(){

        }
        @FXML
        public void btnBack(){
            workingHourWin.stage.close();
        }
    }
}
