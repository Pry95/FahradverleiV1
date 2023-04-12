package com.example.fahradverlei.Windows;

import com.example.fahradverlei.ObjectStruktures.Employee;
import com.example.fahradverlei.ObjectStruktures.Payroll;
import com.example.fahradverlei.ObjectStruktures.WorkingHours;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class PrintPayroll {
    @FXML
    public AnchorPane printPayrollAnchorpane;
    public Label printLabelName;
    public Label printLabelFirstName;
    public Label printLabelStreet;
    public Label printLabelPostalCode;
    public TableView<WorkingHours> tableViewprint;
    public TableColumn<WorkingHours, LocalDate> printDate;
    public TableColumn<WorkingHours, Time> printStart;
    public TableColumn<WorkingHours, Time> printBreakStart;
    public TableColumn<WorkingHours, Time> printBreakEnd;
    public TableColumn<WorkingHours, Time> printEnd;
    public TableColumn<WorkingHours, Double> printTotalHours;
    public static PrintPayroll loadFXML() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(PrintPayroll.class.getResource("/com/example/fahradverlei/PrintPayrollWin.fxml"));
        fxmlLoader.load();
        return fxmlLoader.getController();
    }
    public void fillPrintTableView(List<WorkingHours> myWorkingHoursList){
        printDate.setCellValueFactory(new PropertyValueFactory<>("WorkingDate"));
        printStart.setCellValueFactory(new PropertyValueFactory<>("WorkingStart"));
        printBreakStart.setCellValueFactory(new PropertyValueFactory<>("BreakStart"));
        printBreakEnd.setCellValueFactory(new PropertyValueFactory<>("BreakEnd"));
        printEnd.setCellValueFactory(new PropertyValueFactory<>("WorkEnd"));
        printTotalHours.setCellValueFactory(new PropertyValueFactory<>("TotalHours"));
        tableViewprint.setItems((ObservableList<WorkingHours>) myWorkingHoursList);
    }

    public void printPayroll(Payroll myPayroll, Employee myEmploy, List<WorkingHours> myWorkingHoursList) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean dialogShown = printerJob.showPrintDialog(null);
            if (dialogShown) {
                // Erstellen Sie das Druck-Layout
                PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();

                // Erstellen Sie den Root-Knoten, der gedruckt werden soll
                Parent root = printPayrollAnchorpane; // Erstellen Sie Ihren Root-Knoten hier
                printLabelFirstName.setText(myEmploy.getFirstName());
                printLabelName.setText(myEmploy.getName());
                printLabelStreet.setText(myEmploy.getStreet());
                printLabelPostalCode.setText(String.valueOf(myEmploy.getPostalCode()));
                fillPrintTableView(myWorkingHoursList);

                // Erstellen Sie den Druckbereich
                double scaleX = pageLayout.getPrintableWidth() / root.getBoundsInParent().getWidth();
                double scaleY = pageLayout.getPrintableHeight() / root.getBoundsInParent().getHeight();
                double scale = Math.min(scaleX, scaleY);
                Scale scaleTransform = new Scale(scale, scale);
                root.getTransforms().add(scaleTransform);
                // Erstellen Sie eine Kopie des Root-Knotens für die Druckvorschau
                WritableImage preview = root.snapshot(null, null);


                // Drucken Sie den Root-Knoten
                boolean printed = printerJob.printPage(root);
                if (printed) {
                    printerJob.endJob();
                }
            }
        }
    }




}
