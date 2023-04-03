module com.example.fahradverlei {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fahradverlei to javafx.fxml;
    exports com.example.fahradverlei;
}