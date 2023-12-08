package com.example.finalcis;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class FlightSearchController {

    @FXML private TextField fromCityField;
    @FXML private TextField toCityField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TableView<Flight> flightsTable;
    @FXML private TableColumn<Flight, Number> flightIdColumn;
    @FXML private TableColumn<Flight, String> fromCityColumn;
    @FXML private TableColumn<Flight, String> toCityColumn;
    @FXML private TableColumn<Flight, LocalDate> departureDateColumn;
    @FXML private TableColumn<Flight, LocalTime> departureTimeColumn;
    @FXML private TableColumn<Flight, LocalDate> arrivalDateColumn;
    @FXML private TableColumn<Flight, LocalTime> arrivalTimeColumn;
    @FXML private TableColumn<Flight, Number> capacityColumn;

    private final ObservableList<Flight> flightsList = FXCollections.observableArrayList();

    public void initialize() {
        // Bind the TableView to the ObservableList.
        flightsTable.setItems(flightsList);

        // Initialize cell value factories for the TableView columns
        flightIdColumn.setCellValueFactory(cellData -> cellData.getValue().flightIdProperty());
        fromCityColumn.setCellValueFactory(cellData -> cellData.getValue().fromCityProperty());
        toCityColumn.setCellValueFactory(cellData -> cellData.getValue().toCityProperty());
        departureDateColumn.setCellValueFactory(cellData -> cellData.getValue().departureDateProperty());
        departureTimeColumn.setCellValueFactory(cellData -> cellData.getValue().departureTimeProperty());
        arrivalDateColumn.setCellValueFactory(cellData -> cellData.getValue().arrivalDateProperty());
        arrivalTimeColumn.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty());
        capacityColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty());
    }

    @FXML
    private void searchFlights() {
        // Get the input from the user
        String fromCity = fromCityField.getText();
        String toCity = toCityField.getText();
        LocalDate date = datePicker.getValue();
        LocalTime time = null;

        // Parse the time if not empty and handle parsing errors
        try {
            String timeString = timeField.getText();
            if (!timeString.isEmpty()) {
                time = LocalTime.parse(timeString);
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            // Inform user of the incorrect time format
            return;
        }

        // SQL query
        String query = "SELECT * FROM flights WHERE from_city = ? AND to_city = ? AND departure_date = ? AND departure_time = ?";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fromCity);
            preparedStatement.setString(2, toCity);
            preparedStatement.setDate(3, java.sql.Date.valueOf(date)); // Convert LocalDate to java.sql.Date
            preparedStatement.setTime(4, java.sql.Time.valueOf(time)); // Convert LocalTime to java.sql.Time

            ResultSet resultSet = preparedStatement.executeQuery();
            flightsList.clear(); // Clear the list before adding new items

            while (resultSet.next()) {
                flightsList.add(new Flight(
                        resultSet.getInt("flight_id"),
                        resultSet.getString("from_city"),
                        resultSet.getString("to_city"),
                        resultSet.getDate("departure_date").toLocalDate(),
                        resultSet.getTime("departure_time").toLocalTime(),
                        resultSet.getDate("arrival_date").toLocalDate(),
                        resultSet.getTime("arrival_time").toLocalTime(),
                        resultSet.getInt("capacity")
                ));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions, maybe show an alert to the user
        }
    }

    public void handleBookFlightAction(ActionEvent event) {
    }

    // Other methods can be added as necessary, such as handleBookFlightAction
}
