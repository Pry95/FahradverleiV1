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
public class PayrollWindow {
    public Stage stage;
    public Scene scene;
    public PayrollWindowController controller;
    public MainWin mainWin;
    public PayrollWindow(MainWin mainWin,Employee myEmployee) throws  IOException{
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("payrollWindow.fxml"));
        this.controller = new PayrollWindowController(mainWin, myEmployee, this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 1060, 550);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public class PayrollWindowController implements Initializable{
        public MainWin mainWin;
        public Employee myEmployee;
        public PayrollWindow payrollWindow;
        public PayrollWindowController(MainWin mainWin,Employee myEmployee, PayrollWindow payrollWindow){
            this.payrollWindow = payrollWindow;
            this.mainWin = mainWin;
            this.myEmployee = myEmployee;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
    }
}
