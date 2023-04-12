package com.example.fahradverlei.Windows;

import com.example.fahradverlei.ObjectStruktures.Employee;
import com.example.fahradverlei.ObjectStruktures.Payroll;
import com.example.fahradverlei.ObjectStruktures.WorkingHours;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.util.List;

public class PrintPayroll {
    @FXML
    public AnchorPane printPayrollAnchorpane;
    public Label testlabel;
    public static PrintPayroll loadFXML() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(PrintPayroll.class.getResource("/com/example/fahradverlei/PrintPayrollWin.fxml"));
        fxmlLoader.load();
        return fxmlLoader.getController();
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
                testlabel.setText(myEmploy.getFirstName() +" " + myEmploy.getName());

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
