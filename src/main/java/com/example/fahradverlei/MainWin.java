package com.example.fahradverlei;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainWin {

    public Stage stage;
    public Scene scene;
    public MainWinController controller;

    public MainWin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("MainWin.fxml"));
        this.controller = new MainWinController();
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 1250, 807);
        this.stage = new Stage();
        this.stage.setTitle("Fahrradverleih");
        this.stage.getIcons().add(new Image("file:src/Images/bike.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
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
        public TableColumn<Employee,Double> ColumnBikePricePerDay;
        public TableColumn<Employee,String> ColumnBikeCondition;
        public TableColumn<Employee,String > ColumnBikeContitionComment;
        public TableColumn<Employee,Integer> ColumnBikeBatteryCapacity;
        public TableColumn<Employee,Double> ColumnBikePerformence;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            fillCustomerTableView();
            fillEmployeeTableView();
            fillBikeTableView();
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
            tableViewEmployee.setItems(Database.employeeList);

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
            tabViewCustomer.setItems(Database.customerList);
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
            TableViewBike.setItems(Database.bikeList);
        }
    }
}
