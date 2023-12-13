package com.example.finalcis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class MyFlightsController {

    @FXML
    private TableView<Flight> flightsTableView;

    @FXML
    private TableColumn<Flight, Number> flightIdColumn;
    @FXML
    private TableColumn<Flight, String> fromCityColumn;
    @FXML
    private TableColumn<Flight, String> toCityColumn;
    @FXML
    private TableColumn<Flight, LocalDate> departureDateColumn;
    @FXML
    private TableColumn<Flight, LocalTime> departureTimeColumn;
    @FXML
    private TableColumn<Flight, LocalDate> arrivalDateColumn;
    @FXML
    private TableColumn<Flight, LocalTime> arrivalTimeColumn;
    @FXML
    private TableColumn<Flight, Number> capacityColumn;

    private ObservableList<Flight> userFlightsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        flightIdColumn.setCellValueFactory(cellData -> cellData.getValue().flightIdProperty());
        fromCityColumn.setCellValueFactory(cellData -> cellData.getValue().fromCityProperty());
        toCityColumn.setCellValueFactory(cellData -> cellData.getValue().toCityProperty());
        departureDateColumn.setCellValueFactory(cellData -> cellData.getValue().departureDateProperty());
        departureTimeColumn.setCellValueFactory(cellData -> cellData.getValue().departureTimeProperty());
        arrivalDateColumn.setCellValueFactory(cellData -> cellData.getValue().arrivalDateProperty());
        arrivalTimeColumn.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty());
        capacityColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty());

        flightsTableView.setItems(userFlightsList);
        loadUserFlights();
    }
    @FXML
    private void deleteFlight(ActionEvent event) {
        Flight selectedFlight = flightsTableView.getSelectionModel().getSelectedItem();

        if (selectedFlight !=null) {
            int userId = SessionManager.getInstance().getUserId();
            int flightId= selectedFlight.getFlightId();
            userFlightsList.remove(selectedFlight);
            deleteFlightFromDatabase(userId,flightId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Flight Deleted");
            alert.setHeaderText(null);
            alert.setContentText("Your flight has been deleted");
            alert.showAndWait();
        }
    }

    private void deleteFlightFromDatabase (int userID,int flightId ) {
        String DELETE_QUERY = "DELETE FROM user_flights WHERE user_id=? AND  flight_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1,userID);
            preparedStatement.setInt(2,flightId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadUserFlights() {
        userFlightsList.clear(); // Clear the list before adding new items

        Integer currentUserId = SessionManager.getInstance().getUserId();
        if (currentUserId == null) {
            // Handle the case where no user is logged in
            return;
        }

        String query = "SELECT f.* FROM flights f " +
                "JOIN user_flights uf ON f.flight_id = uf.flight_id " +
                "WHERE uf.user_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentUserId);

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
                userFlightsList.add(flight);
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
    }

    @FXML
    private void handleFlightViewBackButton(ActionEvent event) {
        try {
            // Load the Flight Search view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalcis/LoginScreen.fxml"));
            Parent flightSearchParent = loader.load();
            Scene flightSearchScene = new Scene(flightSearchParent);

            // Get the stage from the event that was triggered by the button click and set the new scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(flightSearchScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


