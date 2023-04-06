package com.example.fahradverlei;

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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RentalWin {

    public Stage stage;
    public Scene scene;
    public RentalWinController controller;
    public MainWin mainWin;

    public RentalWin(MainWin mainWin, Bike tempBike) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("RentalWin.fxml"));
        this.controller = new RentalWinController(mainWin, tempBike, this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 891, 714);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Fahrrad verleihen");
        this.stage.getIcons().add(new Image("file:src/Images/bike.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class RentalWinController implements Initializable {

        public MainWin mainWin;
        public Bike tempBike;
        public RentalWin rentalWin;

        public TableView<Customer> tabViewCustomer;
        public TableColumn<Customer,Integer> columnCustomerID;
        public TableColumn<Customer,String> columnCustomerName;
        public TableColumn<Customer,String> columnCustomerFirstName;
        public TableColumn<Customer, LocalDate> columnCustomerBirth;

        public Label lblBike;
        public Label lblInfo;
        public DatePicker datePickerFrom;
        public DatePicker datePickerTo;

        public Button btnSave;
        public Button btnBack;
        public Button btnDown;
        public Button btnUp;


        public RentalWinController(MainWin mainWin, Bike tempBike, RentalWin rentalWin) {
            this.mainWin = mainWin;
            this.tempBike = tempBike;
            this.rentalWin = rentalWin;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            fillCustomerTableView();

        }

        public void fillCustomerTableView(){
            Database.readCustomerFromDatabase();
            columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerNumber"));
            columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            columnCustomerFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            columnCustomerBirth.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
            tabViewCustomer.setItems(Database.customerList);
        }
        @FXML
        public void btnSave(){

        }
        @FXML
        public void btnBack(){
            rentalWin.stage.close();
        }
        @FXML
        public void btnDown(){

        }
        @FXML
        public void btnUp(){

        }
    }
}
