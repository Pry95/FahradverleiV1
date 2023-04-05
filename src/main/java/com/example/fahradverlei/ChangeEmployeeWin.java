package com.example.fahradverlei;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeEmployeeWin {

    public Stage stage;
    public Scene scene;
    public ChangeEmployeeController controller;
    public MainWin mainWin;

    public ChangeEmployeeWin(MainWin mainWin, Employee tempEmployee) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("ChangeEmployeeWin.fxml"));
        this.controller = new ChangeEmployeeController(mainWin, tempEmployee,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Mitarbeiterdaten ändern");
        this.stage.getIcons().add(new Image("file:src/Images/bike.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class ChangeEmployeeController implements Initializable {

        public MainWin mainWin;
        public Employee tempEmployee;
        public ChangeEmployeeWin changeCustomerWin;

        public TextField txtFieldID;
        public TextField txtFieldFristName;
        public TextField txtFieldName;
        public DatePicker datePickerBirth;
        public TextField txtFieldStreet;
        public TextField txtFieldHouseNumber;
        public TextField txtFieldPLZ;
        public TextField txtFieldTel;
        public TextField txtFieldAccountNumber;
        public Button btnSave;
        public Button btnBack;
        public Label lblInfo;

        // Konstruktor von ChangeEmployeeWinController
        public ChangeEmployeeController(MainWin mainWin, Employee tempEmployee, ChangeEmployeeWin changeBikeWin) {
            this.changeCustomerWin = changeBikeWin;
            this.mainWin = mainWin;
            this.tempEmployee = tempEmployee;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            txtFieldID.setText(String.valueOf(tempEmployee.getEmployeeNumber()));
            txtFieldFristName.setText(tempEmployee.getFirstName());
            txtFieldName.setText(tempEmployee.getName());
            datePickerBirth.setValue(tempEmployee.getBirthDate());
            txtFieldStreet.setText(tempEmployee.getStreet());
            txtFieldHouseNumber.setText(String.valueOf(tempEmployee.getHousenumber()));
            txtFieldPLZ.setText(String.valueOf(tempEmployee.getPostalCode()));
            txtFieldTel.setText(String.valueOf(tempEmployee.getTel()));
            txtFieldAccountNumber.setText(tempEmployee.getAccountNumber());
        }

        @FXML
        public void btnBack(){
            changeCustomerWin.stage.close();
        }
        @FXML
        public void btnSave(){
            try{

            }

            catch (Exception e){
                lblInfo.setText("Falsche Eingabe!");
            }

        }
    }
}
