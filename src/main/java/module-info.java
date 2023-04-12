module com.example.fahradverlei {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.fahradverlei to javafx.fxml;
    exports com.example.fahradverlei;
    exports com.example.fahradverlei.ObjectStruktures;
    opens com.example.fahradverlei.ObjectStruktures to javafx.fxml;
    exports com.example.fahradverlei.Database;
    opens com.example.fahradverlei.Database to javafx.fxml;
    exports com.example.fahradverlei.Windows;
    opens com.example.fahradverlei.Windows to javafx.fxml;

}