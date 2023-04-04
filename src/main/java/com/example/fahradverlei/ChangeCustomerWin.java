package com.example.fahradverlei;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("ChangeCustomerWin.fxml"));
        this.controller = new ChangeCustomerController(mainWin, tempCustomer,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 703, 404);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Kundendaten ändern");
        this.stage.getIcons().add(new Image("file:src/Images/bike.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class ChangeCustomerController implements Initializable {

        public MainWin mainWin;
        public Customer tempCustomer;
        public ChangeCustomerWin changeCustomerWin;

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

        // Konstruktor von ChangeBikeWinController
        public ChangeCustomerController(MainWin mainWin, Customer tempCustomer, ChangeCustomerWin changeBikeWin) {
            this.changeCustomerWin = changeBikeWin;
            this.mainWin = mainWin;
            this.tempCustomer = tempCustomer;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }

        @FXML
        public void btnBack(){
            changeCustomerWin.stage.close();
        }
        @FXML
        public void btnSave(){

        }
    }
}
