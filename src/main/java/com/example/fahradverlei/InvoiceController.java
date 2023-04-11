package com.example.fahradverlei;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class InvoiceController {
    @FXML
    private AnchorPane anchorPaneInvoice;
    public Label lblCustomer;
    public Label lblInvoiceNumber;
    public Label lblDeliverer;
    public Label lblDateDestination;
    public Label lblBike;
    public Label lblTime;
    public PrinterJob printerJob;

    public Stage stage;
    public Parent root;
    public Button btnPrint;

    public static InvoiceController loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InvoiceController.class.getResource("Invoice.fxml"));
        fxmlLoader.load();
        return fxmlLoader.getController();
    }

    public void printInvoice(Rental rental,Bike bike) {
        anchorPaneInvoice.setStyle("-fx-background-color: white;");
        Customer customer = getCustomerFromCustomerList(rental);
        lblCustomer.setText("Name:\t\t" + customer.getName() + " " + customer.getFirstName() +
                "\nAnschrift:\t\t" + customer.getStreet() + " " + customer.getHousenumber() +
                "\nPLZ:\t\t\t" + customer.getPostalCode());
        lblDeliverer.setText("Name:\t\tBikemaster GmbH\nAnschrift:\t\tUrheberverletzungsstraße 45\nPLZ:\t\t\t6969");
        lblInvoiceNumber.setText(String.valueOf(rental.getID()));
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        lblDateDestination.setText(currentDate.format(formatter) + ", Puntigam Links");
        DecimalFormat df = new DecimalFormat("#,##0.00 €");
        lblBike.setText("ID: " + bike.getID() +
                "  |  Bezeichnung: " + bike.getName() +
                "  |  Bauart: " + bike.getDesignType() +
                "  |  Preis / Tag: " + df.format(bike.getPricePerDay()));

        lblTime.setText("Start Datum:  " + rental.getStartDate().toLocalDate().format(formatter) +
                "\nEnd Datum:  " + rental.getEndDate().toLocalDate().format(formatter) +
                "\nTage ausgeliehen: " + ChronoUnit.DAYS.between(rental.getStartDate().toLocalDate(),rental.getEndDate().toLocalDate().plusDays(1)));


        printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            // Erstellen Sie das Druck-Layout
            PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();

            // Erstellen Sie den Root-Knoten, der gedruckt werden soll
            root = anchorPaneInvoice; // Erstellen Sie Ihren Root-Knoten hier

            // Erstellen Sie den Druckbereich
            double scaleX = pageLayout.getPrintableWidth() / root.getBoundsInParent().getWidth();
            double scaleY = pageLayout.getPrintableHeight() / root.getBoundsInParent().getHeight();
            double scale = Math.min(scaleX, scaleY);
            Scale scaleTransform = new Scale(scale, scale);
            root.getTransforms().add(scaleTransform);
        }

        Scene scene = new Scene(anchorPaneInvoice,500,700);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image("file:src/Images/logo.png"));
        stage.setTitle("Druckvorschau");
        stage.setScene(scene);
        stage.show();
    }

    private Customer getCustomerFromCustomerList(Rental item) {
        Customer temp = null;
        for (Customer element: Database.customerList) {
            if(element.getCustomerNumber() == item.getCustomerNumber()){
                temp = element;
                break;
            }

        }
        return temp;
    }

    public void btnPrint(ActionEvent actionEvent) {
        // Drucken Sie den Root-Knoten
        btnPrint.setVisible(false);
        boolean printed = printerJob.printPage(root);
        if (printed) {
            printerJob.endJob();
        }
        stage.close();
    }
}
