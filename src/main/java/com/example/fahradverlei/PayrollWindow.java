package com.example.fahradverlei;
import javafx.collections.ListChangeListener;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
public class PayrollWindow {
    public Stage stage;
    public Scene scene;
    public PayrollWindowController controller;
    public MainWin mainWin;
    public PayrollWindow(MainWin mainWin,Employee myEmployee) throws  IOException{
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("payrollWindow.fxml"));
        this.controller = new PayrollWindowController(mainWin, myEmployee, this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 1060, 550);
        this.stage = new Stage();
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class PayrollWindowController implements Initializable{
        public MainWin mainWin;
        public Employee myEmployee;
        public PayrollWindow payrollWindow;
        public Payroll selectedPayroll;
        //Tableview Payroll
        public TableView<Payroll> payrollTableView;

        public TableColumn <Payroll, Integer> payrollTableViewMonth;
        public TableColumn <Payroll, Integer> payrollTableViewYear;
        public TableColumn <Payroll, Integer> payrollTableViewHoursPerMonth;
        public TableColumn <Payroll, Double> payrollTableViewTotalyHours;
        public TableColumn <Payroll, Double> payrollTableViewOvertime;
        public TableColumn <Payroll, Double> payrollTableViewHourlyWage;
        public TableColumn <Payroll, Double> payrollTableViewGrossSalary;
        public TableColumn <Payroll, Double> payrollTableViewNetSalary;
        public TableColumn <Payroll, Double> payrollTableViewDeductions;

        //TableView monthWorkingHours
        public TableView<WorkingHours> monthWorkingHoursTableView;
        public TableColumn<WorkingHours, LocalDate> monthWorkingHoursTableViewDate;
        public TableColumn<WorkingHours, Time> monthWorkingHoursTableViewStart;
        public TableColumn<WorkingHours,Time> monthWorkingHoursTableViewBreakStart;
        public TableColumn<WorkingHours,Time> monthWorkingHoursTableViewBreakEnd;
        public TableColumn<WorkingHours,Time> monthWorkingHoursTableViewEnd;
        public TableColumn<WorkingHours,Double> monthWorkingHoursTableViewTotalHours;

        public PayrollWindowController(MainWin mainWin,Employee myEmployee, PayrollWindow payrollWindow){
            this.payrollWindow = payrollWindow;
            this.mainWin = mainWin;
            this.myEmployee = myEmployee;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            fillPayrollTabelView();
            updatemonthWorkingHoursTableView();
        }
        public void fillPayrollTabelView(){
            Database.readPayrolsFromaDatabase(myEmployee.getEmployeeNumber());
            payrollTableViewMonth.setCellValueFactory(new PropertyValueFactory<>("Month"));
            payrollTableViewYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
            payrollTableViewHoursPerMonth.setCellValueFactory(new PropertyValueFactory<>("HoursPerMonth"));
            payrollTableViewTotalyHours.setCellValueFactory(new PropertyValueFactory<>("TotalHours"));
            payrollTableViewOvertime.setCellValueFactory(new PropertyValueFactory<>("OverTime"));
            payrollTableViewHourlyWage.setCellValueFactory(new PropertyValueFactory<>("HourlyWage"));
            payrollTableViewGrossSalary.setCellValueFactory(new PropertyValueFactory<>("GrossSalary"));
            payrollTableViewNetSalary.setCellValueFactory(new PropertyValueFactory<>("NetSalary"));
            payrollTableViewDeductions.setCellValueFactory(new PropertyValueFactory<>("Deductions"));
            payrollTableView.setItems(Database.payrollsList);
        }
        public void fillmonthWorkingHoursTableView() {
            Database.readMonthlyWorkingHoursFromDatabase(selectedPayroll.getMonth(), selectedPayroll.getYear(), myEmployee.getEmployeeNumber());
            monthWorkingHoursTableViewDate.setCellValueFactory(new PropertyValueFactory<>("WorkingDate"));
            monthWorkingHoursTableViewStart.setCellValueFactory(new PropertyValueFactory<>("WorkingStart"));
            monthWorkingHoursTableViewBreakStart.setCellValueFactory(new PropertyValueFactory<>("BreakStart"));
            monthWorkingHoursTableViewBreakEnd.setCellValueFactory(new PropertyValueFactory<>("BreakEnd"));
            monthWorkingHoursTableViewEnd.setCellValueFactory(new PropertyValueFactory<>("WorkEnd"));
            monthWorkingHoursTableViewTotalHours.setCellValueFactory(new PropertyValueFactory<>("TotalHours"));
            monthWorkingHoursTableView.setItems(Database.montWorkingHoursList);
        }
        public void updatemonthWorkingHoursTableView(){
             payrollTableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Payroll>() {
                @Override
                public void onChanged(Change<? extends Payroll> change) {
                    selectedPayroll = payrollTableView.getSelectionModel().getSelectedItem();
                    fillmonthWorkingHoursTableView();



                }
            });


        }
    }
}
