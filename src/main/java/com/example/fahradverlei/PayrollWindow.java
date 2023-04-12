package com.example.fahradverlei;
import com.example.fahradverlei.ObjectStruktures.Employee;
import com.example.fahradverlei.ObjectStruktures.Payroll;
import com.example.fahradverlei.ObjectStruktures.WorkingHours;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

public class PayrollWindow {
    public Stage stage;
    public Scene scene;
    public PayrollWindowController controller;
    public MainWin mainWin;
    public PayrollWindow(MainWin mainWin, Employee myEmployee) throws  IOException{
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("payrollWindow.fxml"));
        this.controller = new PayrollWindowController(mainWin, myEmployee, this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 1150, 550);
        this.stage = new Stage();
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class PayrollWindowController implements Initializable {
        public MainWin mainWin;
        public Employee myEmployee;
        public PayrollWindow payrollWindow;
        public Payroll selectedPayroll;
        //Tableview Payroll
        public TableView<Payroll> payrollTableView;

        public TableColumn<Payroll, Integer> payrollTableViewMonth;
        public TableColumn<Payroll, Integer> payrollTableViewYear;
        public TableColumn<Payroll, Integer> payrollTableViewHoursPerMonth;
        public TableColumn<Payroll, Double> payrollTableViewTotalyHours;
        public TableColumn<Payroll, Double> payrollTableViewOvertime;
        public TableColumn<Payroll, Double> payrollTableViewHourlyWage;
        public TableColumn<Payroll, Double> payrollTableViewGrossSalary;
        public TableColumn<Payroll, Double> payrollTableViewNetSalary;
        public TableColumn<Payroll, Double> payrollTableViewDeductions;

        //TableView monthWorkingHours
        public TableView<WorkingHours> monthWorkingHoursTableView;
        public TableColumn<WorkingHours, LocalDate> monthWorkingHoursTableViewDate;
        public TableColumn<WorkingHours, Time> monthWorkingHoursTableViewStart;
        public TableColumn<WorkingHours, Time> monthWorkingHoursTableViewBreakStart;
        public TableColumn<WorkingHours, Time> monthWorkingHoursTableViewBreakEnd;
        public TableColumn<WorkingHours, Time> monthWorkingHoursTableViewEnd;
        public TableColumn<WorkingHours, Double> monthWorkingHoursTableViewTotalHours;
        public ComboBox<Integer> monthWorkingHoursCombobox;
        public ComboBox<Integer> yearWorkingHoursCombobox;
        public Label newPayrollLabel;
        public ImageView payrollImageView;

        public PayrollWindowController(MainWin mainWin, Employee myEmployee, PayrollWindow payrollWindow) {
            this.payrollWindow = payrollWindow;
            this.mainWin = mainWin;
            this.myEmployee = myEmployee;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            fillPayrollTabelView();
            fillCombobox();
            updatemonthWorkingHoursTableView();

        }

        public void fillPayrollTabelView() {
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

        public void fillmonthWorkingHoursTableView(int month, int year, int employeeId) {
            Database.readMonthlyWorkingHoursFromDatabase(month, year, employeeId);
            monthWorkingHoursTableViewDate.setCellValueFactory(new PropertyValueFactory<>("WorkingDate"));
            monthWorkingHoursTableViewStart.setCellValueFactory(new PropertyValueFactory<>("WorkingStart"));
            monthWorkingHoursTableViewBreakStart.setCellValueFactory(new PropertyValueFactory<>("BreakStart"));
            monthWorkingHoursTableViewBreakEnd.setCellValueFactory(new PropertyValueFactory<>("BreakEnd"));
            monthWorkingHoursTableViewEnd.setCellValueFactory(new PropertyValueFactory<>("WorkEnd"));
            monthWorkingHoursTableViewTotalHours.setCellValueFactory(new PropertyValueFactory<>("TotalHours"));
            monthWorkingHoursTableView.setItems(Database.montWorkingHoursList);
        }

        public void fillCombobox() {
            List<Integer> monthList = new ArrayList<>();
            List<Integer> yearList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                monthList.add(i);
            }
            for (int i = 2023; i >= 2000; i--) {
                yearList.add(i);
            }
            monthWorkingHoursCombobox.getItems().addAll(monthList);
            yearWorkingHoursCombobox.getItems().addAll(yearList);
        }

        public void updatemonthWorkingHoursTableView() {
            payrollTableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Payroll>() {
                @Override
                public void onChanged(Change<? extends Payroll> change) {
                    selectedPayroll = payrollTableView.getSelectionModel().getSelectedItem();
                    fillmonthWorkingHoursTableView(selectedPayroll.getMonth(), selectedPayroll.getYear(), myEmployee.getEmployeeNumber());
                    clearnewPayroll();


                }
            });
        }

        public void clearnewPayroll() {
            newPayrollLabel.setText("");
            monthWorkingHoursCombobox.getSelectionModel().clearSelection();
            yearWorkingHoursCombobox.getSelectionModel().clearSelection();
        }

        public void showParollBtn() {
            fillmonthWorkingHoursTableView(Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                    Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()), myEmployee.getEmployeeNumber());
            Payroll myPayroll = newPayroll();
            newPayrollLabel.setText("Monat " + myPayroll.getMonth() + "\nJahr " + myPayroll.getYear() + "\nStd/Mon " + myPayroll.getHoursPerMonth() + "\nTats/Std. " +
                    myPayroll.getTotalHours() + "\nÜberstunden " + myPayroll.getOverTime() + "\nStundenlohn " + myPayroll.getHourlyWage() +
                    "\nBrutto " + myPayroll.getGrossSalary() + "\nNetto " + myPayroll.getNetSalary() + "\nAbzüge " + myPayroll.getDeductions());
        }

        public void payrollBookBtn() {
            fillmonthWorkingHoursTableView(Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                    Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()), myEmployee.getEmployeeNumber());
            Payroll myPayroll = newPayroll();
            try {
                Database.writeNewPayrollInDatabase(myPayroll);
                fillPayrollTabelView();
                clearnewPayroll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Payroll newPayroll() {
            double sumHours = 0;
            double overTime = 0;
            double gross;
            double net;
            double overTimeToPay ;
            for (int i = 0; i < Database.montWorkingHoursList.size(); i++) {
                double tempSum = Database.montWorkingHoursList.get(i).getTotalHours();
                sumHours += tempSum;
            }
            if (myEmployee.getHoursPerMonth() < sumHours) {
                overTime = (sumHours - myEmployee.getHoursPerMonth());
            }
            gross = myEmployee.getHoursPerMonth() * myEmployee.getHourlyWage();
            overTimeToPay = (overTime * myEmployee.getHourlyWage()) * 1.5;
            gross += overTimeToPay;
            net = gross * 0.7;

            Payroll tempPayroll = new Payroll(myEmployee.getEmployeeNumber(), Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                    Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                    myEmployee.getHoursPerMonth(), sumHours, overTime, myEmployee.getHourlyWage(), Math.round(net * 100.0) / 100.0, Math.round((gross * 100.0)) / 100.0, Math.round(((gross - net) * 100.0) / 100.0));
            return tempPayroll;


        }

        public void delPayrollBtn() {
            if (payrollTableView.getSelectionModel().getSelectedItems().size() > 0) {
                Payroll myPayroll = payrollTableView.getSelectionModel().getSelectedItem();
                Database.delPayrollFromDatabase(myPayroll);
                fillPayrollTabelView();
            }
        }
        public  void payrollWinBackBtn(){
            payrollWindow.stage.close();
        }

        public void payrollPrintBtn() throws IOException {

            if (payrollTableView.getSelectionModel().getSelectedItems().size() > 0) {
                Payroll myPayroll = payrollTableView.getSelectionModel().getSelectedItem();
                PrintPayroll invoiceController = PrintPayroll.loadFXML();
                invoiceController.printPayroll(myPayroll,myEmployee,Database.montWorkingHoursList);

            }
        }

    }
}
