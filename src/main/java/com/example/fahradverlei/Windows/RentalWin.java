package com.example.fahradverlei.Windows;

import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Bike;
import com.example.fahradverlei.ObjectStruktures.Customer;
import com.example.fahradverlei.ObjectStruktures.Rental;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class RentalWin {

    public Stage stage;
    public Scene scene;
    public RentalWinController controller;
    public MainWin mainWin;

    public RentalWin(MainWin mainWin, Bike tempBike) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("/com/example/fahradverlei/RentalWin.fxml"));
        this.controller = new RentalWinController(mainWin, tempBike, this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 891, 780);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Fahrrad verleihen");
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class RentalWinController implements Initializable {

        // Übergebene Objekte
        public MainWin mainWin;
        public Bike tempBike;
        public RentalWin rentalWin;

        // TableView für Objekt Customer
        public TableView<Customer> tabViewCustomer;
        public TableColumn<Customer, Integer> columnCustomerID;
        public TableColumn<Customer, String> columnCustomerName;
        public TableColumn<Customer, String> columnCustomerFirstName;
        public TableColumn<Customer, LocalDate> columnCustomerBirth;

        // TableView für Objekt Rental
        public TableView<Rental> tableViewRental;
        public TableColumn<Rental,Integer>columnID;
        public TableColumn<Rental, Date> columnFrom;
        public TableColumn<Rental, Date> columnTo;
        public TableColumn<Rental, Integer> columnBikeID;
        public TableColumn<Rental, String> columnBike;
        public TableColumn<Rental, String> columnType;
        public TableColumn<Rental, Integer> columnCustID;
        public TableColumn<Rental, String> columnCustomer;
        public TableColumn<Rental, String> columnPayed;
        public TableColumn<Rental, String> columnDuplikate;
        public TableColumn<Rental,Date> columnPayDate;

        // Elemente der FXML
        public Label lblBike;
        public Label lblInfo;
        public Label lblRentalInfo;
        public DatePicker datePickerFrom;
        public DatePicker datePickerTo;
        public TextField txtFieldSearch;
        public TextField txtFieldSearchRental;
        public ComboBox<String> comboboxFilter;

        // Konstruktor von ChangeBikeWinController
        public RentalWinController(MainWin mainWin, Bike tempBike, RentalWin rentalWin) {
            this.mainWin = mainWin;
            this.tempBike = tempBike;
            this.rentalWin = rentalWin;
        }

        // Methode die beim Start des neuen Fensters aufgerufen wird
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            // beüllt die Fenster Elemente
            fillComboBoxFilter();
            fillCustomerTableView();
            fillRentalTableView();
            lblBike.setText("ID: " + tempBike.getID() + "\t\tBezeichnung: " + tempBike.getName() + "\t\tArt: " + tempBike.getDesignType());


            /**Sperrt die folgenden Tage vom DatepickerFrom:
             * alle Tage in der Vergangenheit
             * alle Tage die bereits in der rentalList vergeben sind
             */
            datePickerFrom.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                    for (Rental element : Database.rentalList) {
                        if (date.isBefore(element.getEndDate().toLocalDate().plusDays(1)) && date.isAfter(element.getStartDate().toLocalDate().minusDays(1))) {
                            setDisable(true);
                        }
                    }
                }
            });

            /** Setzt einen Listener auf das Element DatePickerFrom und führt die folgenden Schritte aus:
             * aktuelle Auswahl wird auf DatepickerTo übertragen
             * DatepickerTo: alle Werte die vor DatePickerFrom sind, sind gesperrt
             * DatepickerTo: alle Werte die in der RenatlList bereits vergeben sind sind gesperrt
            */
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
                            if (date.isBefore(element.getEndDate().toLocalDate().plusDays(1)) && date.isAfter(element.getStartDate().toLocalDate().minusDays(1))) {
                                setDisable(true);
                            }
                            if (newValue.isBefore(element.getStartDate().toLocalDate())) {
                                if (date.isAfter(element.getStartDate().toLocalDate())) {
                                    setDisable(true);
                                }
                            }
                        }
                    }
                });
            });
        }

        /** befüllt die ComboboxFilter und löscht bei einer Auswahl den Inhalt vom txtFieldSearchRental:
         */
        public void fillComboBoxFilter(){
            comboboxFilter.getItems().addAll("RechnungsNr","Art","KundenID","Kunde");
            comboboxFilter.setValue("RechnungsNr");
            comboboxFilter.setOnAction(event -> {
                txtFieldSearchRental.clear();

            });
        }

        private void fillRentalTableView() {

            Database.readRentalFromDatabase(tempBike.getID());
            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnFrom.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
            columnTo.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
            columnBikeID.setCellValueFactory(new PropertyValueFactory<>("BikeID"));
            columnBike.setCellValueFactory(new PropertyValueFactory<>("BikeName"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            columnCustID.setCellValueFactory(new PropertyValueFactory<>("CustomerNumber"));
            columnCustomer.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
            columnPayed.setCellValueFactory(new PropertyValueFactory<>("Payed"));
            columnDuplikate.setCellValueFactory(new PropertyValueFactory<>("Duplikate"));
            columnPayDate.setCellValueFactory((new PropertyValueFactory<>("PayDate")));

            // Filter für die TableviewRental, Filtertext ist in txtFieldSearchRental
            FilteredList<Rental> filteredData = new FilteredList<>(Database.rentalList, p -> true);
            txtFieldSearchRental.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(rental -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Filtert nach Rechnungnummer
                    if (Objects.equals(comboboxFilter.getValue(), "RechnungsNr") && Objects.equals(rental.getID(), Integer.valueOf(newValue))) {
                        return true;
                    }
                    // Filtert nach Art
                    else if (Objects.equals(comboboxFilter.getValue(), "Art") && rental.getType().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    }
                    // Filtert nach KundenID
                    else if (Objects.equals(comboboxFilter.getValue(), "KundenID") && Objects.equals(rental.getCustomerNumber(), Integer.valueOf(newValue))) {
                        return true;
                    }
                    // Filtert nach Kunde
                    else if (Objects.equals(comboboxFilter.getValue(), "Kunde") && rental.getCustomerName().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    }
                        return false;
                });
            });
            SortedList<Rental> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableViewRental.comparatorProperty());
            tableViewRental.setItems(sortedData);
        }


        public void fillCustomerTableView() {

            Database.readCustomerFromDatabase();
            columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerNumber"));
            columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            columnCustomerFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            columnCustomerBirth.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));

            // Filter für die TableviewRental, Filtertext ist in txtFieldSearch
            FilteredList<Customer> filteredData = new FilteredList<>(Database.customerList, p -> true);
            txtFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String name = person.getFirstName() + " " + person.getName();
                    String name2 = person.getName() + " " + person.getFirstName();
                    String lowerCaseFilter = newValue.toLowerCase();

                    // Filtert nach Namen
                    if (name.toLowerCase().contains(lowerCaseFilter.toLowerCase())) {
                        return true;
                    }
                    // Filtert nach Namen
                    else if (name2.toLowerCase().contains(lowerCaseFilter.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Customer> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tabViewCustomer.comparatorProperty());
            tabViewCustomer.setItems(sortedData);
        }

        // Speichert eine Reservierung in die Datenbank
        @FXML
        public void btnSave() {
            if (tabViewCustomer.getSelectionModel().getSelectedItems().size() > 0) {
                Customer tempCustomer = tabViewCustomer.getSelectionModel().getSelectedItem();
                if (datePickerFrom.getValue() != null && datePickerTo.getValue() != null) {
                    Database.writeRentalToDatabase(tempBike, tempCustomer.getCustomerNumber(), datePickerFrom.getValue(), datePickerTo.getValue(), "VERLEIH");
                    fillRentalTableView();
                    lblInfo.setText("Fahrrad ist reserviert!");
                    datePickerTo.setValue(null);
                    datePickerFrom.setValue(null);
                } else {
                    lblInfo.setText("Datum fehlt!");
                }
            } else {
                lblInfo.setText("Kunde auswählen!");
            }
        }

        // Speichert eine Wartung in die Datenbank
        @FXML
        public void btnRepair() {
            if (datePickerFrom.getValue() != null && datePickerTo.getValue() != null) {
                Database.writeRentalToDatabase(tempBike, 99, datePickerFrom.getValue(), datePickerTo.getValue(), "WARTUNG");
                fillRentalTableView();
                lblInfo.setText("Fahrradwartung reserviert!");
                datePickerTo.setValue(null);
                datePickerFrom.setValue(null);
            } else {
                lblInfo.setText("Datum fehlt!");
            }
        }

        @FXML
        public void btnBack() {
            rentalWin.stage.close();
        }

        @FXML
        public void btnDelFilterCustomer() {
            txtFieldSearch.clear();
        }

        // Ändert den Datenbankeintrag des ausgewählten Elements auf Payed "ja" und PayDate "now"
        @FXML
        public void btnPay() {
            if (tableViewRental.getSelectionModel().getSelectedItems().size() > 0) {
                Rental item = tableViewRental.getSelectionModel().getSelectedItem();
                if (!Objects.equals(item.getType(), "WARTUNG")){
                    if (!Objects.equals(item.isPayed(), "ja")){
                        lblRentalInfo.setText("");
                        Database.changeRentalDataFromDataBase(item, "pay",LocalDate.now());
                        fillRentalTableView();
                    }
                }
             else {
                lblRentalInfo.setText("Einträge vom Typ Wartung können nicht bezahlt werden werden!");
            }
            }
        }

        // Ändert den Datenbankeintrag des ausgewählten Elements auf ptinted "ja" und startet den Druckvorgang
        @FXML
        public void btnPrint() throws IOException {

            if (tableViewRental.getSelectionModel().getSelectedItems().size() > 0) {
                Rental item = tableViewRental.getSelectionModel().getSelectedItem();
                    if (!Objects.equals(item.getType(), "WARTUNG")){
                        if (!Objects.equals(item.isDuplikate(), "ja")){
                            lblRentalInfo.setText("");
                            Database.changeRentalDataFromDataBase(item,"print",null);
                            fillRentalTableView();
                        }
                        InvoiceController invoiceController = InvoiceController.loadFXML();
                        invoiceController.printInvoice(item,tempBike);

                    }

                    else{
                        lblRentalInfo.setText("Einträge vom Typ Wartung können nicht gedruckt werden!");
                    }
                }
            }
        }
    }

