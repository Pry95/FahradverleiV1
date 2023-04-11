package com.example.fahradverlei;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
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
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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
        this.scene = new Scene(fxmlLoader.load(), 891, 400);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Fahrrad verleihen");
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
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

        public TableView<Rental> tableViewRental;
        public TableColumn<Rental, Date> columnFrom;
        public TableColumn<Rental, Date> columnTo;
        public TableColumn<Rental,Integer> columnBikeID;
        public TableColumn<Rental,String > columnBike;
        public TableColumn<Rental,String> columnType;
        public TableColumn<Rental,Integer> columnCustID;
        public TableColumn<Rental,String> columnCustomer;


        public Label lblBike;
        public Label lblInfo;
        public DatePicker datePickerFrom;
        public DatePicker datePickerTo;

        public Button btnSave;
        public Button btnBack;
        public Button btnDown;
        public Button btnUp;
        public Button btnRepair;
        public TextField txtFieldSearch;


        public RentalWinController(MainWin mainWin, Bike tempBike, RentalWin rentalWin) {
            this.mainWin = mainWin;
            this.tempBike = tempBike;
            this.rentalWin = rentalWin;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            fillCustomerTableView();
            fillRentalTableView();

            lblBike.setText("ID: " + tempBike.getID() + "\t\tBezeichnung: " + tempBike.getName());

            Image img = new Image("file:src/Images/down.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(15);
            view.setPreserveRatio(true);
            btnDown.setGraphic(view);

            Image img2 = new Image("file:src/Images/up.png");
            ImageView view2 = new ImageView(img2);
            view2.setFitHeight(15);
            view2.setPreserveRatio(true);
            btnUp.setGraphic(view2);


            datePickerFrom.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                    for (Rental element : Database.rentalList) {
                        if (date.isBefore(element.getEndDate().toLocalDate().plusDays(1)) && date.isAfter(element.getStartDate().toLocalDate().minusDays(1))){
                            setDisable(true);
                        }
                    }
                }
            });

            datePickerFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
                datePickerTo.setValue(newValue);
                datePickerTo.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.isBefore(newValue)) {
                            setDisable(true);
                        }
                        for (Rental element : Database.rentalList) {
                            if (date.isBefore(element.getEndDate().toLocalDate().plusDays(1)) && date.isAfter(element.getStartDate().toLocalDate().minusDays(1))){
                                setDisable(true);
                            }
                            if (newValue.isBefore(element.getStartDate().toLocalDate())){
                                if (date.isAfter(element.getStartDate().toLocalDate())){
                                    setDisable(true);
                                }
                            }
                        }
                    }
                });
            });

        }

        private void fillRentalTableView() {
            Database.readRentalFromDatabase(tempBike.getID());
            columnFrom.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
            columnTo.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
            columnBikeID.setCellValueFactory(new PropertyValueFactory<>("BikeID"));
            columnBike.setCellValueFactory(new PropertyValueFactory<>("BikeName"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            columnCustID.setCellValueFactory(new PropertyValueFactory<>("CustomerNumber"));
            columnCustomer.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
            tableViewRental.setItems(Database.rentalList);
        }

        public void fillCustomerTableView(){

            Database.readCustomerFromDatabase();
            columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerNumber"));
            columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            columnCustomerFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            columnCustomerBirth.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));

            FilteredList<Customer> filteredData = new FilteredList<>(Database.customerList, p -> true);
            txtFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
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
        @FXML
        public void btnSave(){
            if (tabViewCustomer.getSelectionModel().getSelectedItems().size() > 0){
                Customer tempCustomer = tabViewCustomer.getSelectionModel().getSelectedItem();
                if (datePickerFrom.getValue() != null && datePickerTo.getValue() != null){
                    Database.writeRentalToDatabase(tempBike,tempCustomer.getCustomerNumber(),datePickerFrom.getValue(),datePickerTo.getValue(),"VERLEIH");
                    fillRentalTableView();
                    lblInfo.setText("Fahrrad ist reserviert!");
                    datePickerTo.setValue(null);
                    datePickerFrom.setValue(null);
                }
                else {
                    lblInfo.setText("Datum fehlt!");
                }
            }
            else {
                lblInfo.setText("Kunde auswählen!");
            }
        }
        @FXML
        public void btnRepair(){
            if (datePickerFrom.getValue() != null && datePickerTo.getValue() != null){
                Database.writeRentalToDatabase(tempBike,99,datePickerFrom.getValue(),datePickerTo.getValue(),"WARTUNG");
                fillRentalTableView();
                lblInfo.setText("Fahrradwartung reserviert!");
                datePickerTo.setValue(null);
                datePickerFrom.setValue(null);
            }
            else {
                lblInfo.setText("Datum fehlt!");
            }
        }
        @FXML
        public void btnBack(){
            rentalWin.stage.close();
        }
        @FXML
        public void btnDown(){

            rentalWin.stage.setHeight(755);
        }
        @FXML
        public void btnUp(){
            rentalWin.stage.setHeight(435);
        }

        @FXML
        public void btnDelFilterCustomer(){
            txtFieldSearch.clear();
        }
    }
}
