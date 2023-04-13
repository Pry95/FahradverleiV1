package com.example.fahradverlei.Windows;

import com.example.fahradverlei.Database.Database;
import com.example.fahradverlei.ObjectStruktures.Bike;
import com.example.fahradverlei.ObjectStruktures.Customer;
import com.example.fahradverlei.ObjectStruktures.Rental;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class InvoiceController {

    // FXML Elemente
    public AnchorPane anchorPaneInvoice;
    public Label lblCustomer;
    public Label lblInvoiceNumber;
    public Label lblDeliverer;
    public Label lblDateDestination;
    public Label lblBike;
    public Label lblTime;
    public Label lblPrice;
    public Label lblEndPrice;
    public Label lblDuplikate;
    public Label lblPayed;
    public Button btnPrint;


    // PrinterJob für das PDF File
    private PrinterJob printerJob;

    // Fenster Druckvorschau
    private Stage stage;

    // Vorlage für Druck
    private Parent root;

    // Pfad für den Speichervorgang des PDF
    private final String firstPartOfPath = System.getProperty("user.home") + "/Desktop" + "\\Rechnungen\\";
    private String completePath;


    // Controller für den Druck der Rechnung
    public static InvoiceController loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InvoiceController.class.getResource("/com/example/fahradverlei/Invoice.fxml"));
        fxmlLoader.load();
        return fxmlLoader.getController();
    }

    // Methode für den Druckvorgang + Skalieren der Rechnung
    public void printInvoice(Rental rental, Bike bike) {
        // Erstellt den Ordner Rechnungen am Desktop falls es ihn noch nicht gibt
        File folder = new File(firstPartOfPath);
        folder.mkdir();

        // Erstellt den kompletten Pfad für den Speichervorgang des PDF (ORIGINAL)
        String endPartOfPath = rental.getCustomerName().replace(" ", "") + "_ReNr" + rental.getID() + ".pdf";
        completePath = firstPartOfPath + endPartOfPath;

        // ergänzt im Pfad "DU" (DUPLIKAT)
        if (Objects.equals(rental.isDuplikate(), "ja")){
            completePath = completePath.substring(0,completePath.length()-4) + "_Du.pdf";
        }

        // Formatierungsvorlagen für Datum und Preis
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00 €");

        // Anchorpane Hintergrund auf Weiß
        anchorPaneInvoice.setStyle("-fx-background-color: white;");

        // gibt den gesuchten Customer zurück
        Customer customer = getCustomerFromCustomerList(rental);

        //befüllt die Label der Rechnung mit den übergebenen Daten
        lblCustomer.setText(customer.stringForInvoiceTitel());
        lblDeliverer.setText("Name:\t\tBikemaster GmbH\nAnschrift:\t\tUrheberverletzungsstraße 45\nPLZ:\t\t\t6969");
        lblInvoiceNumber.setText(String.valueOf(rental.getID()));
        lblDateDestination.setText(LocalDate.now().format(dateFormatter) + ", Puntigam Links");
        lblBike.setText(bike.stringForInvoice());

        // Errechnet die Tage zwischen Startdatum und Enddatum
        Integer days = (int)ChronoUnit.DAYS.between(rental.getStartDate().toLocalDate(),rental.getEndDate().toLocalDate().plusDays(1));

        //befüllt die Label der Rechnung mit den übergebenen Daten
        lblTime.setText("Start Datum:\t\t\t" + rental.getStartDate().toLocalDate().format(dateFormatter) +
                "\nEnd Datum:\t\t\t" + rental.getEndDate().toLocalDate().format(dateFormatter) +
                "\nTage ausgeliehen:\t\t" + days);

        // Errechnet den gesamt Preis
        double price = days * bike.getPricePerDay();

        // befüllt die Label der Rechnung mit den übergebenen Daten
        lblPrice.setText("(Tage)\t\t" + days + "\nx\n(Preis / Tag)\t" + decimalFormatter.format(bike.getPricePerDay()) +"\n=");
        lblEndPrice.setText("Summe:\t\t" + decimalFormatter.format(price));

        // wenn das Rental Objekt Duplikat "ja" ist dann wird auf der Rechnung DUPLIKAT aufgedruckt
        if (Objects.equals(rental.isDuplikate(), "ja")){
            lblDuplikate.setText("Duplikat");
        }

        // wenn das Rental Objekt PayDate nicht null ist dann wird auf der Rechnung "bezahlt am: ...." aufgedruckt
        if (rental.getPayDate() != null){
            lblPayed.setText("bezahlt am: " + rental.getPayDate().toLocalDate().format(dateFormatter));
        }

        //Drucker für den Druckjob auswählen --> Microsoft Print to PDF
        for (Printer printer : Printer.getAllPrinters()) {
            if (printer.getName().equals("Microsoft Print to PDF")) {
                printerJob = PrinterJob.createPrinterJob();
                printerJob.setPrinter(printer);
                break;
            }
        }
        //Druckjob
        if (printerJob != null) {
            // Erstellt das Drucklayout
            PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();

            // Erstellt den Root Knotend er gedruckt werden soll
            root = anchorPaneInvoice;

            // Erstellt den Druckbereich und skaliert ihn
            double scaleX = (pageLayout.getPrintableWidth()) / root.getBoundsInParent().getWidth();
            double scaleY = pageLayout.getPrintableHeight() / root.getBoundsInParent().getHeight();
            double scale = Math.min(scaleX, scaleY);
            Scale scaleTransform = new Scale(scale, scale);
            root.getTransforms().add(scaleTransform);
        }
        // Legt den Drucknamen und den Pfad für den Druck fest
        printerJob.getJobSettings().setJobName("output.pdf");
        printerJob.getJobSettings().setOutputFile( completePath);

        // erstellt ein Fenster für die Druckvorschau
        Scene scene = new Scene(anchorPaneInvoice,500,700);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image("file:src/Images/logo.png"));
        stage.setTitle("Druckvorschau");
        stage.setScene(scene);
        stage.show();
    }

    public void btnPrint(ActionEvent actionEvent) throws IOException {

        // versteckt den Print Button und schließt danach die Druckvorschau
        btnPrint.setVisible(false);
        stage.close();

        // legt ein Objekt vom Typ File an das später zum Öffnen des PDF's benötigt wird
        File temp = new File(completePath);

        // Druckvorgang
        boolean printed = printerJob.printPage(root);
        if (printed) {
                printerJob.endJob();
        }

        // setzt eine Wartezeit von einer Sekunde fest
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            // Versucht das erstellte PDF File direkt zu öffnen
            try {
                Desktop.getDesktop().open(temp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Start des Wartezeit Methode
        delay.play();
    }

    /** Durchsucht die CustomerList über die ID des Rental Objekts und gibt den gefundenen Customer zurück
     * @param rental Enthält die CustomerID nach der gesucht werden soll
     */
    private Customer getCustomerFromCustomerList(Rental rental) {
        Customer temp = null;
        for (Customer element: Database.customerList) {
            if(element.getCustomerNumber() == rental.getCustomerNumber()){
                temp = element;
                break;
            }
        }
        return temp;
    }
}