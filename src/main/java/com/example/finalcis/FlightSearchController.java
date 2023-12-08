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

public class FlightSearchController {

    public Button bookFlightButton;
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

    public void initialize() {
        // Set up the TableView cell value factories
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
    protected void handleSearchAction(ActionEvent event) {
        searchFlights();
    }

    private void searchFlights() {
        String fromCity = fromCityField.getText();
        String toCity = toCityField.getText();
        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.parse(timeField.getText()); // Make sure to validate and handle parsing errors

        String query = "SELECT * FROM flights WHERE from_city = ? AND to_city = ? AND DATE(departure_time) = ? AND TIME(departure_time) = ?";

        ObservableList<Flight> flights = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fromCity);
            preparedStatement.setString(2, toCity);
            preparedStatement.setObject(3, date);
            preparedStatement.setObject(4, time);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight(
                        resultSet.getInt("flight_id"),
                        resultSet.getString("from_city"),
                        resultSet.getString("to_city"),
                        resultSet.getDate("departure_date").toLocalDate(),
                        resultSet.getTime("departure_time").toLocalTime(),
                        resultSet.getDate("arrival_date").toLocalDate(),
                        resultSet.getTime("arrival_time").toLocalTime(),
                        resultSet.getInt("capacity")
                );
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        flightsTable.setItems(flights);
    }

    public void handleBookFlightAction(ActionEvent event) {
    }
}
