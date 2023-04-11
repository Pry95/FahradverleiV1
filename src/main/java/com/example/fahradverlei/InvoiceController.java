package com.example.fahradverlei;

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

public class InvoiceController {
    @FXML
    private AnchorPane anchorPaneInvoice;
    public Label lblCustomer;
    public static InvoiceController loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InvoiceController.class.getResource("Invoice.fxml"));
        fxmlLoader.load();
        return fxmlLoader.getController();
    }

    public void printInvoice() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean dialogShown = printerJob.showPrintDialog(null);
            if (dialogShown) {
                // Erstellen Sie das Druck-Layout
                PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();

                // Erstellen Sie den Root-Knoten, der gedruckt werden soll
                Parent root = anchorPaneInvoice; // Erstellen Sie Ihren Root-Knoten hier

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
