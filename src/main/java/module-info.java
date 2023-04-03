module com.example.fahradverlei {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.fahradverlei to javafx.fxml;
    exports com.example.fahradverlei;
}