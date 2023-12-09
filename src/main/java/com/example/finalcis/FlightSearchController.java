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

    @FXML Label overBooked;
    @FXML Label bookFlightLabel;
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

    @FXML
    public void handleBookFlightAction(ActionEvent event) {
        Flight selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            int userId = getCurrentUserId(); // This will now retrieve the ID from the session.
            int flightId = selectedFlight.getFlightId();
            LocalDate bookingDate = LocalDate.now(); // Assuming the booking date is the current date.

            bookFlightForUser(userId, flightId, bookingDate);
        } else {
            System.out.println("Please select a flight");
        }
    }


    private void bookFlightForUser(int userId, int flightId, LocalDate bookingDate) {

        if (isFlightFull(flightId)) {
            overBooked.setText("This flight is full");
            return;
        }

        if (isFlightAlreadyBooked(userId, flightId)) {
            overBooked.setText("You already booked this flight");
            return;
        }

        // Check for overlapping flights
        if (hasOverlappingFlights(userId, flightId)) {
            overBooked.setText("You have a over lapping flight");
            return;
        }

        String insertQuery = "INSERT INTO user_flights (user_id, flight_id, booking_date) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, flightId);
            preparedStatement.setObject(3, bookingDate);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                bookFlightLabel.setText("Book Flight Successful");
            } else {
                // Booking failed, inform the user.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions, maybe show an alert to the user.
        } finally {
            // Ensure resources are closed after the operation is complete
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
    }


    private int getCurrentUserId() {
        Integer userId = SessionManager.getInstance().getUserId();
        if (userId == null) {
            // Handle case where the user ID is not set, which means no user is logged in.
            // You might throw an exception or show an error message.
        }
        return userId;
    }

    private boolean isFlightAlreadyBooked(int userId, int flightId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) FROM user_flights WHERE user_id = ? AND flight_id = ?";

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, flightId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return false;
    }

    private boolean hasOverlappingFlights(int userId, int newFlightId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) FROM user_flights uf " +
                "JOIN flights f ON uf.flight_id = f.flight_id " +
                "JOIN flights newf ON newf.flight_id = ? " +
                "WHERE uf.user_id = ? AND ((f.departure_time < newf.arrival_time " +
                "AND f.arrival_time > newf.departure_time) OR (f.arrival_time > newf.departure_time " +
                "AND f.departure_time < newf.arrival_time))";

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newFlightId);
            preparedStatement.setInt(2, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return false;
    }

    private boolean isFlightFull(int flightId) {
        String capacityQuery = "SELECT capacity FROM flights WHERE flight_id = ?";
        String bookingCountQuery = "SELECT COUNT(*) FROM user_flights WHERE flight_id = ?";
        int capacity = 0;
        int bookingCount = 0;

        Connection connection = null;
        PreparedStatement capacityStatement = null;
        PreparedStatement bookingCountStatement = null;
        ResultSet capacityResultSet = null;
        ResultSet bookingCountResultSet = null;

        try {
            connection = DBConnection.getConnection();

            // Check the flight's capacity
            capacityStatement = connection.prepareStatement(capacityQuery);
            capacityStatement.setInt(1, flightId);
            capacityResultSet = capacityStatement.executeQuery();
            if (capacityResultSet.next()) {
                capacity = capacityResultSet.getInt("capacity");
            }

            // Check the number of bookings
            bookingCountStatement = connection.prepareStatement(bookingCountQuery);
            bookingCountStatement.setInt(1, flightId);
            bookingCountResultSet = bookingCountStatement.executeQuery();
            if (bookingCountResultSet.next()) {
                bookingCount = bookingCountResultSet.getInt(1);
            }

            return bookingCount >= capacity;
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Assume full on error to prevent overbooking
        } finally {
            if (capacityResultSet != null) try { capacityResultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (bookingCountResultSet != null) try { bookingCountResultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (capacityStatement != null) try { capacityStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (bookingCountStatement != null) try { bookingCountStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
