package com.example.fahradverlei.Windows;

import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Employee;
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("/com/example/fahradverlei/ChangeEmployeeWin.fxml"));
        this.controller = new ChangeEmployeeController(mainWin, tempEmployee,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Mitarbeiterdaten ändern");
        this.stage.getIcons().add(new Image("file:src/Images/logo.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class ChangeEmployeeController implements Initializable {

        // Übergebene Objekte
        public MainWin mainWin;
        public Employee tempEmployee;
        public ChangeEmployeeWin changeEmployeeWin;

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
        public TextField txtFieldHourlyWage;
        public TextField txtFieldHoursPerMonth;

        public Label lblInfo;

        // Konstruktor von ChangeEmployeeWinController
        public ChangeEmployeeController(MainWin mainWin, Employee tempEmployee, ChangeEmployeeWin changeEmployeeWin) {
            this.changeEmployeeWin = changeEmployeeWin;
            this.mainWin = mainWin;
            this.tempEmployee = tempEmployee;
        }

        // Methode die beim Start des neuen Fensters aufgerufen wird
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            // befüllt die Elemente der FXML mit dem übergebenen Objekt Customer
            txtFieldID.setText(String.valueOf(tempEmployee.getEmployeeNumber()));
            txtFieldFristName.setText(tempEmployee.getFirstName());
            txtFieldName.setText(tempEmployee.getName());
            datePickerBirth.setValue(tempEmployee.getBirthDate());
            txtFieldStreet.setText(tempEmployee.getStreet());
            txtFieldHouseNumber.setText(String.valueOf(tempEmployee.getHousenumber()));
            txtFieldPLZ.setText(String.valueOf(tempEmployee.getPostalCode()));
            txtFieldTel.setText(String.valueOf(tempEmployee.getTel()));
            txtFieldAccountNumber.setText(tempEmployee.getAccountNumber());
            txtFieldHoursPerMonth.setText(String.valueOf(tempEmployee.getHoursPerMonth()));
            txtFieldHourlyWage.setText(String.valueOf(tempEmployee.getHourlyWage()));
        }

        @FXML
        public void btnBack(){
            changeEmployeeWin.stage.close();
        }
        @FXML
        public void btnSave(){

            // versucht einen Employee aus dem Inhalt der FXML Elemente zu erstellen
            try{
                Employee temp = new Employee(
                        txtFieldFristName.getText(),
                        txtFieldName.getText(),
                        datePickerBirth.getValue(),
                        txtFieldStreet.getText(),
                        txtFieldHouseNumber.getText(),
                        Integer.parseInt(txtFieldPLZ.getText()),
                        Integer.parseInt(txtFieldTel.getText()),
                        Double.parseDouble(txtFieldHourlyWage.getText()),
                        Integer.parseInt(txtFieldHoursPerMonth.getText()),
                        txtFieldAccountNumber.getText(),
                        Integer.parseInt(txtFieldID.getText())
                        );
                // Überschreibt die Daten des Employee in der Datenbank
                Database.changeEmployeeDataFromDataBase(temp);
                // TableView neu befüllen / refreshen
                mainWin.controller.fillEmployeeTableView();
                // Fenster schließen
                changeEmployeeWin.stage.close();
            }

            catch (Exception e){
                lblInfo.setText("Falsche Eingabe!");
            }
        }
    }
}
