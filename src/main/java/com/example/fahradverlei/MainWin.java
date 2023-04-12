package com.example.fahradverlei;

import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Bike;
import com.example.fahradverlei.ObjectStruktures.Customer;
import com.example.fahradverlei.ObjectStruktures.EBike;
import com.example.fahradverlei.ObjectStruktures.Employee;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWin {

    public Stage stage;
    public Scene scene;
    public MainWinController controller;

    public MainWin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("MainWin.fxml"));
        this.controller = new MainWinController(this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 1250, 807);
        this.stage = new Stage();
        this.stage.setTitle("Fahrradverleih");
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
    }

    public class MainWinController implements Initializable {

        public TableView<Customer> tabViewCustomer;
        public TableColumn<Customer,Integer> columnCustomerID;
        public TableColumn<Customer,String> columnCustomerName;
        public TableColumn<Customer,String> columnCustomerFirstName;
        public TableColumn<Customer, LocalDate> columnCustomerBirth;
        public TableColumn<Customer,String> columnCustomerStreet;
        public TableColumn<Customer,String> columnCustomerHouseNumber;
        public TableColumn<Customer,Integer> columnCustomerPlz;
        public TableColumn<Customer,Integer> columnCustomerTel;
        public TableColumn<Customer,String> columnCustomerAccount;


        public TableView<Employee> tableViewEmployee;
        public TableColumn<Employee,Integer> ColumnEmployeeID;
        public TableColumn<Employee,String> ColumnEmployeeName;
        public TableColumn<Employee,String> ColumnEmployeeFirstName;
        public TableColumn<Employee, LocalDate> ColumnEmployeeBirth;
        public TableColumn<Employee,String> ColumnEmployeeStreets;
        public TableColumn<Employee,String> ColumnEmployeeHouseNumber;
        public TableColumn<Employee,Integer> ColumnEmployeePLZ;
        public TableColumn<Employee,Integer> ColumnEmployeeTel;
        public TableColumn<Employee,Double> ColumnEmployeeHourlyWage;
        public TableColumn<Employee,Integer> ColumnEmployeeHoursPerMonth;
        public TableColumn<Employee,Integer>ColumnEmployeeAccountNumber;

        //Bike tablewidget
        public TableView<Bike>TableViewBike;

        public TableColumn<Bike,Integer> ColumnBikeId;
        public TableColumn<Bike,String> ColumnBikeName;
        public TableColumn<Bike,Integer> ColumnBikeFrameSize;
        public TableColumn<Bike, String> ColumnBikeDisign;
        public TableColumn<Bike,Double> ColumnBikePricePerDay;
        public TableColumn<Bike,String> ColumnBikeCondition;
        public TableColumn<Bike,String > ColumnBikeContitionComment;
        public TableColumn<Bike,Integer> ColumnBikeBatteryCapacity;
        public TableColumn<Bike,Double> ColumnBikePerformence;


        // Bike invest
        public ImageView imageViewBikeInvest;
        public ComboBox<String>comboBoxInvestBikeType;
        public ComboBox<Integer>comboBoxInvestBikeFrameSize;
        public TextField textFieldBikeInvestName;
        public TextField textFieldBikeInvestPricePerDay;
        public TextField textFieldBikeInvestBatteryCapacity;
        public TextField textFieldBikeInvestPerformance;
        // Customer invest
        public TextField textFieldCustomerFirstname;
        public TextField textFieldCustomerName;
        public DatePicker datePickerCustomer;
        public TextField textFieldCustomerStreet;
        public TextField textFieldCustomerHouseNumber;
        public TextField textFieldCustomerPostalCode;
        public TextField textFieldCustomerTel;
        public TextField textFieldCustomerAccount;
        public TextField datePickerEmployeeInsert;
        public TextField textFieldEmployeeName;
        public TextField textFieldEmployeeFirstName;
        public DatePicker datePickerEmployee;
        public TextField textFieldEmployeeStreet;
        public TextField textFieldEmployeeHouseNumber;
        public TextField textFieldEmployeePostalCode;
        public TextField textFieldEmployeeTel;
        public TextField textFieldEmployeeHourlyWage;
        public TextField textFieldEmployeeHoursPerMonth;
        public TextField textFieldEmployeeAccount;
        public MainWin mainWin;
        public Button btnInvestNewBike;
        public Button btnDelBike;
        public Button btnChangeBike;
        public Button btnDelCustomer;
        public Button btnChangeCustomer;
        public Button btnRepair;


        public Button btnChangeEmployee;
        public Button btnDelEmployee;
        public TextField txtFieldSearchBike;
        public TextField txtFieldSearchCustomer;
        public TextField txtFieldSearchEmployee;


        // Konstruktor von MainWinController
        public MainWinController(MainWin mainWin){
            this.mainWin = mainWin;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            fillCustomerTableView();
            fillEmployeeTableView();
            fillBikeTableView();
            fillBikesCombobox();
        }

        public void fillEmployeeTableView(){
            Database.readEmployeeFromDatabase();
            ColumnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("EmployeeNumber"));
            ColumnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            ColumnEmployeeFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            ColumnEmployeeBirth.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
            ColumnEmployeeStreets.setCellValueFactory(new PropertyValueFactory<>("Street"));
            ColumnEmployeeHouseNumber.setCellValueFactory(new PropertyValueFactory<>("Housenumber"));
            ColumnEmployeePLZ.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
            ColumnEmployeeTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
            ColumnEmployeeHourlyWage.setCellValueFactory(new PropertyValueFactory<>("HourlyWage"));
            ColumnEmployeeHoursPerMonth.setCellValueFactory(new PropertyValueFactory<>("HoursPerMonth"));
            ColumnEmployeeAccountNumber.setCellValueFactory(new PropertyValueFactory<>("AccountNumber"));

            ColumnEmployeeHourlyWage.setCellFactory(column -> {
                return new TableCell<Employee, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.format("%.2f €", item));
                        }
                    }
                };
            });
            FilteredList<Employee> filteredData = new FilteredList<>(Database.employeeList, p -> true);
            txtFieldSearchEmployee.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String name = person.getFirstName() + " " + person.getName();
                    String name2 = person.getName() + " " + person.getFirstName();
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (name.toLowerCase().contains(lowerCaseFilter.toLowerCase())) {
                        return true;
                    } else if (name2.toLowerCase().contains(lowerCaseFilter.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Employee> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableViewEmployee.comparatorProperty());
            tableViewEmployee.setItems(sortedData);
        }

        public void fillCustomerTableView(){
            Database.readCustomerFromDatabase();
            columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerNumber"));
            columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            columnCustomerFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            columnCustomerBirth.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
            columnCustomerStreet.setCellValueFactory(new PropertyValueFactory<>("Street"));
            columnCustomerHouseNumber.setCellValueFactory(new PropertyValueFactory<>("Housenumber"));
            columnCustomerPlz.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
            columnCustomerTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
            columnCustomerAccount.setCellValueFactory(new PropertyValueFactory<>("AccountNumber"));

            ColumnEmployeeHourlyWage.setCellFactory(column -> {
                return new TableCell<Employee, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.format("%.2f €", item));
                        }
                    }
                };
            });
            FilteredList<Customer> filteredData = new FilteredList<>(Database.customerList, p -> true);
            txtFieldSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String name = person.getFirstName() + " " + person.getName();
                    String name2 = person.getName() + " " + person.getFirstName();
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (name.toLowerCase().contains(lowerCaseFilter.toLowerCase())) {
                        return true;
                    } else if (name2.toLowerCase().contains(lowerCaseFilter.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Customer> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tabViewCustomer.comparatorProperty());
            tabViewCustomer.setItems(sortedData);
        }
        public void fillBikeTableView(){
            Database.readBikesFromDatabase();
            ColumnBikeId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColumnBikeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            ColumnBikeFrameSize.setCellValueFactory(new PropertyValueFactory<>("FrameSize"));
            ColumnBikeDisign.setCellValueFactory(new PropertyValueFactory<>("DesignType"));
            ColumnBikePricePerDay.setCellValueFactory(new PropertyValueFactory<>("PricePerDay"));
            ColumnBikeCondition.setCellValueFactory(new PropertyValueFactory<>("Condition"));
            ColumnBikeContitionComment.setCellValueFactory(new PropertyValueFactory<>("ConditionComment"));
            ColumnBikeBatteryCapacity.setCellValueFactory(new PropertyValueFactory<>("BatteryCapacity"));
            ColumnBikePerformence.setCellValueFactory(new PropertyValueFactory<>("Performance"));
            // Fügt das € Zeichen in der BikeTableView bei PreisPerDay hinzu
            ColumnBikePricePerDay.setCellFactory(column -> {
                return new TableCell<Bike, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.format("%.2f €", item));
                        }
                    }
                };
            });

            FilteredList<Bike> filteredData = new FilteredList<>(Database.bikeList, p -> true);
            txtFieldSearchBike.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Bike> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(TableViewBike.comparatorProperty());
            TableViewBike.setItems(sortedData);
        }
        public void fillBikesCombobox(){
            List<Integer> frameSizeList = new ArrayList<>();
            for(int i = 14;i <= 64;i++){
                frameSizeList.add(i);
            }
            comboBoxInvestBikeType.getItems().addAll("Rennrad", "Citybike", "Bmx", "EBike", "MountainBike");
            comboBoxInvestBikeFrameSize.getItems().addAll(frameSizeList);

        }
        @FXML
        public void btnInvestNewBike(){

            String typ = comboBoxInvestBikeType.getSelectionModel().getSelectedItem().toString();
            String name = textFieldBikeInvestName.getText();
            double pricePerDay = Double.parseDouble(textFieldBikeInvestPricePerDay.getText());
            int frameSize = Integer.parseInt(comboBoxInvestBikeFrameSize.getSelectionModel().getSelectedItem().toString());
            if(typ.equals("EBike")){
                try{
                    Database.writeNewElectroBikeInDatabase(new EBike(1,textFieldBikeInvestName.getText(),comboBoxInvestBikeFrameSize.getSelectionModel().getSelectedItem().toString(),comboBoxInvestBikeType.getSelectionModel().getSelectedItem().toString(),
                            Double.parseDouble(textFieldBikeInvestPricePerDay.getText()),"Sehr Gut", "Passt soweit alles", Integer.parseInt(textFieldBikeInvestBatteryCapacity.getText()),Integer.parseInt(textFieldBikeInvestPerformance.getText())));
                    JOptionPane.showMessageDialog(null, "Ebike hinzugefügt");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Falsche Eingabe");
                    setBikeInsertWindow();
                    throw new RuntimeException(e);
                }

            }else{
                try{
                    Database.writeNewBikeInDatabase(new Bike(1,textFieldBikeInvestName.getText(),comboBoxInvestBikeFrameSize.getSelectionModel().getSelectedItem().toString(),comboBoxInvestBikeType.getSelectionModel().getSelectedItem().toString(),
                            Double.parseDouble(textFieldBikeInvestPricePerDay.getText()),"Sehr Gut", "Passt soweit alles"));
                    JOptionPane.showMessageDialog(null, "Bike hinzugefügt");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Falsche Eingabe");
                    setBikeInsertWindow();
                    throw new RuntimeException(e);
                }
            }
            setBikeInsertWindow();
            fillBikeTableView();
        }
        @FXML
        //schreibt einen neuen datensatz in die Tabelle customer
        public void btnCustomerIInsert(){
            try{
                Customer myCustomer = new Customer(textFieldCustomerFirstname.getText(),textFieldCustomerName.getText(),datePickerCustomer.getValue(),textFieldCustomerStreet.getText(),textFieldCustomerHouseNumber.getText(),
                        Integer.parseInt(textFieldCustomerPostalCode.getText()),Integer.parseInt(textFieldCustomerTel.getText()),textFieldCustomerAccount.getText(),1);
                Database.writeNewCustomerInDatabase(myCustomer);
                setCustomerInsertWindow();
                JOptionPane.showMessageDialog(null, "Benutzer hinzugefügt");
                fillCustomerTableView();

            } catch (Exception e) {
                setCustomerInsertWindow();
                JOptionPane.showMessageDialog(null, "Falsche Eingabe");
                throw new RuntimeException(e);
            }
        }
        @FXML
        public void btnEmployeeInsert(){
            try {
                Employee myEmployee = new Employee(textFieldEmployeeFirstName.getText(),textFieldEmployeeName.getText(),datePickerEmployee.getValue(),textFieldEmployeeStreet.getText(),
                        textFieldEmployeeHouseNumber.getText(),Integer.parseInt(textFieldEmployeePostalCode.getText()),Integer.parseInt(textFieldEmployeeTel.getText()),
                        Double.parseDouble(textFieldEmployeeHourlyWage.getText()),Integer.parseInt(textFieldEmployeeHoursPerMonth.getText()),textFieldEmployeeAccount.getText(),1);
                Database.writeNewEmployeeInDatabase(myEmployee);
                setEmployeeInsertWindow();
                JOptionPane.showMessageDialog(null, "Mitarbeiter hinzugefügt");
                fillEmployeeTableView();
            } catch (NumberFormatException e) {
                setEmployeeInsertWindow();
                JOptionPane.showMessageDialog(null, "Falsche Eingabe");
                throw new RuntimeException(e);

            }
        }

        // Löscht ein Bike aus der Datenbank und aktualisiert die TableViewBike
        @FXML
        public void btnDelBike(){
            if (TableViewBike.getSelectionModel().getSelectedItems().size() > 0){
                Bike tempBike = TableViewBike.getSelectionModel().getSelectedItem();
                Database.delBikeFromDatabase(tempBike);
                fillBikeTableView();
            }
        }
        // Erstellt ein neues Fenster wo die Daten für das ausgewählte Fahrrad geändert werden können
        @FXML
        public void btnChangeBike() throws IOException {
            if (TableViewBike.getSelectionModel().getSelectedItems().size() > 0){
                Bike tempBike = TableViewBike.getSelectionModel().getSelectedItem();
                ChangeBikeWin changeBikeWin = new ChangeBikeWin(mainWin,tempBike);
            }
        }
        @FXML
        public void setBikeInsertWindow(){
            textFieldBikeInvestBatteryCapacity.clear();
            textFieldBikeInvestName.clear();
            textFieldBikeInvestPerformance.clear();
            textFieldBikeInvestPricePerDay.clear();
            comboBoxInvestBikeFrameSize.getSelectionModel().clearSelection();
            comboBoxInvestBikeType.getSelectionModel().clearSelection();
        }
        public void setCustomerInsertWindow(){
            textFieldCustomerAccount.clear();
            textFieldCustomerFirstname.clear();
            textFieldCustomerStreet.clear();
            textFieldCustomerHouseNumber.clear();
            textFieldCustomerPostalCode.clear();
            textFieldCustomerTel.clear();
            textFieldCustomerName.clear();
            datePickerCustomer.setValue(null);
        }
        public void setEmployeeInsertWindow(){

            textFieldEmployeeName.clear();
            textFieldEmployeeFirstName.clear();
            datePickerEmployee.setValue(null);
            textFieldEmployeeStreet.clear();
            textFieldEmployeeHouseNumber.clear();
            textFieldEmployeePostalCode.clear();
            textFieldEmployeeTel.clear();
            textFieldEmployeeHourlyWage.clear();
            textFieldEmployeeHoursPerMonth.clear();
            textFieldEmployeeAccount.clear();
        }

        @FXML
        public void btnDelCustomer(){
            if (tabViewCustomer.getSelectionModel().getSelectedItems().size() > 0){
                Customer tempCustomer = tabViewCustomer.getSelectionModel().getSelectedItem();
                Database.delCustomerFromDatabase(tempCustomer);
                fillCustomerTableView();
            }
        }
        @FXML
        public void  btnChangeCustomer() throws IOException {
            if (tabViewCustomer.getSelectionModel().getSelectedItems().size() > 0){
                Customer tempCustomer = tabViewCustomer.getSelectionModel().getSelectedItem();
                ChangeCustomerWin changeCustomerWin = new ChangeCustomerWin(mainWin,tempCustomer);
            }
        }
        @FXML
        public void btnDelEmployee(){
            if (tableViewEmployee.getSelectionModel().getSelectedItems().size() > 0){
                Employee tempEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
                Database.delEmployeeFromDatabase(tempEmployee);
                fillEmployeeTableView();
            }
        }
        @FXML
        public void btnChangeEmployee() throws IOException {
            if (tableViewEmployee.getSelectionModel().getSelectedItems().size() > 0){
                Employee tempEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
                ChangeEmployeeWin changeEmployeeWin = new ChangeEmployeeWin(mainWin,tempEmployee);
            }
        }
        @FXML
        public void btnEmployeeWorkingTime() throws IOException {
            if (tableViewEmployee.getSelectionModel().getSelectedItems().size() > 0){
                Employee tempEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
                WorkingHourWin workingHourWin = new WorkingHourWin(mainWin,tempEmployee);
            }
        }
        @FXML
        /**
         * Erzeigt das Fenster Payroll
         */
        public void btnPayroll()throws IOException{
            if (tableViewEmployee.getSelectionModel().getSelectedItems().size() > 0) {
                Employee tempEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
                PayrollWindow payrollWindow = new PayrollWindow(mainWin, tempEmployee);
            }
        }
        @FXML
        public void btnRent() throws IOException {
            if (TableViewBike.getSelectionModel().getSelectedItems().size() > 0){
                Bike tempBike = TableViewBike.getSelectionModel().getSelectedItem();
                RentalWin rentalWin = new RentalWin(mainWin,tempBike);
            }
        }

        @FXML
        public void btnDelFilterBike(){
            txtFieldSearchBike.clear();
        }
        @FXML
        public void btnDelFilterCustomer(){
            txtFieldSearchCustomer.clear();
        }
        @FXML
        public void btnDelFilterEmployee(){
            txtFieldSearchEmployee.clear();
        }
    }
}
