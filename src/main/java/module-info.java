module com.example.finalcis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.finalcis to javafx.fxml;
    exports com.example.finalcis;
}