package com.example.fahradverlei.Windows;
import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Customer;
import com.example.fahradverlei.ObjectStruktures.Employee;
import com.example.fahradverlei.ObjectStruktures.Payroll;
import com.example.fahradverlei.ObjectStruktures.WorkingHours;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
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

    /** Diese Methode erstellt ein neues Fenster für die Lohnabrechnung eines Mitarbeiters und
     *  konfiguriert es mit einer FXML-Datei und den erforderlichen Eigenschaften.
     */
    public PayrollWindow(MainWin mainWin, Employee myEmployee) throws  IOException{
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("/com/example/fahradverlei/payrollWindow.fxml"));
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
        public TextField searchPayrollTextfield;
        public Button clearTxtFieldBtn;





        /** Estellt einen neuen Controller und erhölt Referenzen auf das Mainwin, den Übergebenen Mitarbeiter
         * und die Gehaltsabrechnung.
         */
        public PayrollWindowController(MainWin mainWin, Employee myEmployee, PayrollWindow payrollWindow) {
            this.payrollWindow = payrollWindow;
            this.mainWin = mainWin;
            this.myEmployee = myEmployee;
        }


        /** Diese Methode füllt eine Tabelle mit Lohnabrechnungsinformationen, füllt eine Combobox mit Monaten und
         *  Jahren und aktualisiert eine Tabelle mit Arbeitsstunden.
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            fillPayrollTabelView();
            fillCombobox();
            updatemonthWorkingHoursTableView();

        }


        /** Füllt die Gehaltsabrechnungen in die Tableview
         */
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

            FilteredList<Payroll> filteredData = new FilteredList<>(Database.payrollsList, p -> true);
            searchPayrollTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(myPayroll -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String year = String.valueOf(myPayroll.getYear());
                    if(year.contains(newValue)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Payroll> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(payrollTableView.comparatorProperty());
            payrollTableView.setItems(sortedData);
        }


        /** Füllt die entsprechenden Arbeitszeiten in die Tableview.
         */

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


        /** Füllt die Comboboxen mir Jahren und Monaten
         */
        public void fillCombobox() {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            List<Integer> monthList = new ArrayList<>();
            List<Integer> yearList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                monthList.add(i);
            }
            for (int i = currentYear; i >= 2000; i--) {
                yearList.add(i);
            }
            monthWorkingHoursCombobox.getItems().addAll(monthList);
            yearWorkingHoursCombobox.getItems().addAll(yearList);
        }


        /** Updated die Arbeitszeiten Tableview mit den zur Ausgewählten Gehaltsabrechnung passenden Arbeitszeiten.
         */
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


        /** Setzt das Label für die Vorschau einer neune Gehalbtabrechnung auf einen leeren String und
         * löscht die auswahl aus den Comboboxen.
         */
        public void clearnewPayroll() {
            newPayrollLabel.setText("");
            monthWorkingHoursCombobox.getSelectionModel().clearSelection();
            yearWorkingHoursCombobox.getSelectionModel().clearSelection();
        }


        /** Zeigt dei Vorschau einer Gehaltsabrechnung.
         */
        public void showParollBtn() {

            int month = 0;
            int year = 0;
            try{
                month = Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString());
                year = Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString());
            } catch (Exception e) {

            }
            // Prüft ob Jahr und Monat ausgewält wurden.
            if(month != 0 && year !=0) {
                // Arbeitszeiten Tableview wird mit enstprechenden Arbeitszeiten befüllt.
                // Hier werden Jahr, Monat und Mitarbeiter Id übergeben.
                fillmonthWorkingHoursTableView(Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()), myEmployee.getEmployeeNumber());
                Payroll myPayroll = newPayroll();
                //Hier wird der String für die Vorschau der neuen Gehaltsabrechnung gestaltet.
                newPayrollLabel.setText("Monat " + myPayroll.getMonth() +
                        "\nJahr " + myPayroll.getYear() +
                        "\nStd/Mon " + myPayroll.getHoursPerMonth() +
                        "\nTats/Std. " + myPayroll.getTotalHours() +
                        "\nÜberstunden " + myPayroll.getOverTime() +
                        "\nStundenlohn " + myPayroll.getHourlyWage() +
                        "\nBrutto " + myPayroll.getGrossSalary() +
                        "\nNetto " + myPayroll.getNetSalary() +
                        "\nAbzüge " + myPayroll.getDeductions());

            }
        }


        /** Hier wird eine neue Gehaltsabrechnung in die Tabelle `Payroll´ geschrieben.
         */
        public void payrollBookBtn() {
            int month = 0;
            int year = 0;
            try{
                month = Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString());
                year = Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString());
            } catch (Exception e) {

            }
            // Prüft ob Jahr und Monat ausgewält wurden.
            if(month != 0 && year !=0) {
                // Arbeitszeiten Tableview wird mit enstprechenden Arbeitszeiten befüllt.
                // Hier werden Jahr, Monat und Mitarbeiter Id übergeben.
                fillmonthWorkingHoursTableView(Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()), myEmployee.getEmployeeNumber());
                Payroll myPayroll = newPayroll();
                try {
                    // Hier wird die erstelle Gehaltsabrechnung in die Datenbank geschrieben.
                    Database.writeNewPayrollInDatabase(myPayroll);
                    fillPayrollTabelView();
                    clearnewPayroll();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gehaltsabrechnung beteits vorhanden!");
                    throw new RuntimeException(e);
                }
            }
        }


        /** Hier wird eine neue Gehaltsabrechnung (Payroll) erstellt.
         */
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
            //Hier wird die Gehaltsabrechnung erzeugt und die Werte auf 2 Nachkommastellen Gerundet.
            Payroll tempPayroll = new Payroll(myEmployee.getEmployeeNumber(), Integer.parseInt(monthWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                    Integer.parseInt(yearWorkingHoursCombobox.getSelectionModel().getSelectedItem().toString()),
                    myEmployee.getHoursPerMonth(), Math.round((sumHours * 100) / 100.0), Math.round((overTime * 100) / 100.0), myEmployee.getHourlyWage(), Math.round(net * 100.0) / 100.0, Math.round((gross * 100.0)) / 100.0, Math.round(((gross - net) * 100.0) / 100.0));
            return tempPayroll;


        }


        /** Hier wird eine Gehaltsabrechnung aus Tabelle `Payroll´ gelöscht.
         */
        public void delPayrollBtn() {
            ImageIcon delIcon = new ImageIcon("src/Images/IconDel.png");
            if (payrollTableView.getSelectionModel().getSelectedItems().size() > 0) {
                Payroll myPayroll = payrollTableView.getSelectionModel().getSelectedItem();
                int antwort = JOptionPane.showConfirmDialog(null, "Kunde wirklich löschen", "Kunde löschen", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,delIcon);
                if(antwort == JOptionPane.YES_NO_OPTION){
                    Database.delPayrollFromDatabase(myPayroll);
                    searchPayrollTextfield.clear();
                    fillPayrollTabelView();
                    monthWorkingHoursTableView.getItems().clear();
                }
            }
        }
        public  void payrollWinBackBtn(){
            payrollWindow.stage.close();
        }


        /** Hier wird der Druchvorgang für die Gehaltsabrechnung gestarted.
         */
        public void payrollPrintBtn() throws IOException {

            if (payrollTableView.getSelectionModel().getSelectedItems().size() > 0) {
                Payroll myPayroll = payrollTableView.getSelectionModel().getSelectedItem();
                PrintPayroll invoiceController = PrintPayroll.loadFXML();
                invoiceController.printPayroll(myPayroll,myEmployee,Database.montWorkingHoursList);

            }
        }
        public void clearTxtFieldBtn(){
            searchPayrollTextfield.clear();
        }

    }
}
