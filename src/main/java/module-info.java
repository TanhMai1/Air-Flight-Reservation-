module com.example.finalcis {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalcis to javafx.fxml;
    exports com.example.finalcis;
}