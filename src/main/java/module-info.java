module com.example.finalcis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.finalcis to javafx.fxml;
    exports com.example.finalcis;
}