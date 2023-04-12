package com.example.fahradverlei.Windows;

import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Employee;
import com.example.fahradverlei.ObjectStruktures.WorkingHours;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("/com/example/fahradverlei/WorkingHourWin.fxml"));
        this.controller = new WorkingHourController(mainWin, tempEmployee, this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Arbeitszeiten verwalten");
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class WorkingHourController implements Initializable {

        public MainWin mainWin;
        public Employee tempEmployee;
        public WorkingHourWin workingHourWin;
        public ComboBox<Time> comboStart;
        public ComboBox<Time> comboEnd;
        public ComboBox<Time> comboBreakStart;
        public ComboBox<Time> comboBreakEnd;
        public DatePicker datepicker;
        public Label lblEmployee;
        public Label lblInfo;
        public TableView<WorkingHours> tableViewWorkingHour;
        public TableColumn<WorkingHours, LocalDate> ColumnDate;
        public TableColumn<WorkingHours,Time> ColumnStart;
        public TableColumn<WorkingHours,Time> ColumnBreakStart;
        public TableColumn<WorkingHours,Time> ColumnBreakEnd;
        public TableColumn<WorkingHours,Time> ColumnEnd;
        public TableColumn<WorkingHours,Double> ColumnHours;



        public WorkingHourController(MainWin mainWin, Employee tempEmployee, WorkingHourWin workingHourWin) {
            this.mainWin = mainWin;
            this.tempEmployee = tempEmployee;
            this.workingHourWin = workingHourWin;
        }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            lblEmployee.setText("ID: " + tempEmployee.getEmployeeNumber() + " \tName: " + tempEmployee.getFirstName() + " " + tempEmployee.getName());
            fillComboBoxesTime();
            fillTableViewWorkingHour();
        }

        private void fillTableViewWorkingHour() {
            ColumnDate.setCellValueFactory(new PropertyValueFactory<>("WorkingDate"));
            ColumnStart.setCellValueFactory(new PropertyValueFactory<>("WorkingStart"));
            ColumnBreakStart.setCellValueFactory(new PropertyValueFactory<>("BreakStart"));
            ColumnBreakEnd.setCellValueFactory(new PropertyValueFactory<>("BreakEnd"));
            ColumnEnd.setCellValueFactory(new PropertyValueFactory<>("WorkEnd"));
            ColumnHours.setCellValueFactory(new PropertyValueFactory<>("TotalHours"));
            Database.readWorkingHoursFromDatabase(tempEmployee.getEmployeeNumber());
            tableViewWorkingHour.setItems(Database.workingHoursList);
        }

        public void fillComboBoxesTime(){
            List<Time> times = new ArrayList<>();
            Time time = Time.valueOf(LocalTime.of(6,0));
            while (!time.equals(Time.valueOf(LocalTime.of(22,5)))) {
                times.add(time);
                time = Time.valueOf(time.toLocalTime().plusMinutes(5));
            }
            comboStart.getItems().addAll(times);
            comboStart.setValue(Time.valueOf(LocalTime.of(7,0)));
            comboEnd.getItems().addAll(times);
            comboEnd.setValue(Time.valueOf(LocalTime.of(15,30)));
            comboBreakEnd.getItems().addAll(times);
            comboBreakEnd.setValue(Time.valueOf(LocalTime.of(12,30)));
            comboBreakStart.getItems().addAll(times);
            comboBreakStart.setValue(Time.valueOf(LocalTime.of(12,0)));
            datepicker.setValue(LocalDate.now());
            // lässt im Datepicker keine Auswahl von Tagen in der Zukunft oder Sonntagen zu
            datepicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date.isAfter(LocalDate.now())) {
                        setDisable(true);
                    }
                    if (date.getDayOfWeek() == DayOfWeek.SUNDAY){
                        setDisable(true);
                    }
                    for (WorkingHours element : Database.workingHoursList) {
                        if (date.isEqual(element.getWorkingDate())){
                            setDisable(true);
                        }
                    }
                }
            });

        }
        @FXML
        public void btnSave(){

            if (comboStart.getValue().getTime() < comboBreakStart.getValue().getTime() &&
                    comboBreakStart.getValue().getTime() < comboBreakEnd.getValue().getTime() &&
                    comboBreakEnd.getValue().getTime() < comboEnd.getValue().getTime() &&
                    proofIfDateExists(datepicker.getValue())){
                try{
                    long hours =  (comboEnd.getValue().getTime() -comboStart.getValue().getTime()) -
                            (comboBreakEnd.getValue().getTime()-comboBreakStart.getValue().getTime()) ;
                    double hour = Math.round(((double)hours /1000/60/60) * 100.0) / 100.0;
                    WorkingHours workingHour = new WorkingHours(
                            datepicker.getValue(),
                            comboStart.getValue(),
                            comboBreakStart.getValue(),
                            comboBreakEnd.getValue(),
                            comboEnd.getValue(),hour
                    );
                    Database.writeWorkingHoursToDatabase(tempEmployee,workingHour);
                    fillTableViewWorkingHour();
                    lblInfo.setText("Eintrag wurde hinzugefügt!");
                }
                catch (Exception e){
                    lblInfo.setText("Falsche Eingabe!");
                }
            }
            else{
                lblInfo.setText("Falsche Eingabe!");
            }
        }
        public boolean proofIfDateExists(LocalDate date){
            int temp = 0;
            for(WorkingHours element : Database.workingHoursList){
                if(element.getWorkingDate().isEqual(date)){
                    temp++;
                }
            }
            return temp <= 0;
        }
        @FXML
        public void btnBack(){
            workingHourWin.stage.close();
        }
    }
}
