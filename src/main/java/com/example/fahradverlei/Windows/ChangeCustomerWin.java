package com.example.fahradverlei.Windows;

import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeCustomerWin {

    public Stage stage;
    public Scene scene;
    public ChangeCustomerController controller;
    public MainWin mainWin;

    public ChangeCustomerWin(MainWin mainWin, Customer tempCustomer) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("/com/example/fahradverlei/ChangeCustomerWin.fxml"));
        this.controller = new ChangeCustomerController(mainWin, tempCustomer,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Kundendaten ändern");
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class ChangeCustomerController implements Initializable {

        // Übergebene Objekte
        public MainWin mainWin;
        public Customer tempCustomer;
        public ChangeCustomerWin changeCustomerWin;

        // Elemente der FXML
        public TextField txtFieldID;
        public TextField txtFieldFristName;
        public TextField txtFieldName;
        public DatePicker datePickerBirth;
        public TextField txtFieldStreet;
        public TextField txtFieldHouseNumber;
        public TextField txtFieldPLZ;
        public TextField txtFieldTel;
        public TextField txtFieldAccountNumber;

        public Label lblInfo;

        // Konstruktor von ChangeBikeWinController
        public ChangeCustomerController(MainWin mainWin, Customer tempCustomer, ChangeCustomerWin changeBikeWin) {
            this.changeCustomerWin = changeBikeWin;
            this.mainWin = mainWin;
            this.tempCustomer = tempCustomer;
        }

        // Methode die beim Start des neuen Fensters aufgerufen wird
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            // befüllt die Elemente der FXML mit dem übergebenen Objekt Customer
            txtFieldID.setText(String.valueOf(tempCustomer.getCustomerNumber()));
            txtFieldFristName.setText(tempCustomer.getFirstName());
            txtFieldName.setText(tempCustomer.getName());
            datePickerBirth.setValue(tempCustomer.getBirthDate());
            txtFieldStreet.setText(tempCustomer.getStreet());
            txtFieldHouseNumber.setText(String.valueOf(tempCustomer.getHousenumber()));
            txtFieldPLZ.setText(String.valueOf(tempCustomer.getPostalCode()));
            txtFieldTel.setText(String.valueOf(tempCustomer.getTel()));
            txtFieldAccountNumber.setText(tempCustomer.getAccountNumber());
        }

        @FXML
        public void btnBack(){
            changeCustomerWin.stage.close();
        }
        @FXML
        public void btnSave(){
            // versucht einen Customer aus dem Inhalt der FXML Elemente zu erstellen
            try{
                Customer temp = new Customer(
                        txtFieldFristName.getText(),
                        txtFieldName.getText(),
                        datePickerBirth.getValue(),
                        txtFieldStreet.getText(),
                        txtFieldHouseNumber.getText(),
                        Integer.parseInt(txtFieldPLZ.getText()),
                        Integer.parseInt(txtFieldTel.getText()),
                        txtFieldAccountNumber.getText(),
                        Integer.parseInt(txtFieldID.getText())
                );
                // Überschreibt die Daten des Customers in der Datenbank
                Database.changeCustomerDataFromDataBase(temp);
                // TableView neu befüllen / refreshen
                mainWin.controller.fillCustomerTableView();
                // Fenster schließen
                changeCustomerWin.stage.close();
            }

            catch (Exception e){
                lblInfo.setText("Falsche Eingabe!");
            }

        }
    }
}
