package com.example.finalcis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Label forgotPasswordMessageLabel;
    @FXML
    private TextField answer;
    @FXML
    private Label usernameRetrieved;
    @FXML
    private Label wrongAnswer;


    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        try {
            Integer userId = authenticate(usernameField.getText(), passwordField.getText());
            if (userId != null) {
                System.out.println("Login successful!");
                SessionManager.getInstance().setUserId(userId); // Set the user ID in the session manager
                System.out.println(userId);

                // Load the "Hello" view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalcis/LoginScreen.fxml"));
                Parent mainScreenParent = loader.load();

                // If MainScreen has a controller that needs to know the user ID, you can set it like this:
                // MainScreenController controller = loader.getController();
                // controller.setUserId(userId);

                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            } else {
                System.out.println("Login failed. Incorrect username or password.");
                loginMessageLabel.setText("Incorrect username or password.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading the Main screen.");
            e.printStackTrace();
        }
    }



    private Integer authenticate(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id"); // Get user ID from the result set
            }
            return null; // Return null if authentication failed
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleBookFlightButton(ActionEvent event) {
        try {
            // Load the Flight Search view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalcis/FlightSearch.fxml"));
            Parent flightSearchParent = loader.load();
            Scene flightSearchScene = new Scene(flightSearchParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(flightSearchScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRegistrationAction(ActionEvent event) {
        try {
            // Load the Register form view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalcis/RegistrationScreen.fxml"));
            Parent RegistrationScreenParent = loader.load();
            Scene RegistrationScreenScene = new Scene(RegistrationScreenParent);

            // Get the stage from the event that was triggered by the button click and set the new scene
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(RegistrationScreenScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, possibly show an error dialog to the user
        }
    }
    public void handleForgotPasswordAction(ActionEvent event) {
        String username = usernameField.getText();
        if (!username.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalcis/ForgotPassword.fxml"));
                Parent ForgotPasswordScreenParent = loader.load();
                Scene ForgotPasswordScreenScene = new Scene(ForgotPasswordScreenParent);

                // Get the stage from the event that was triggered by the button click and set the new scene
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(ForgotPasswordScreenScene);
                window.show();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            forgotPasswordMessageLabel.setText("Please enter your username.");
        }
    }

    public void forgotPassword() {
        String answer = this.answer.getText();
        String username = usernameField.getText();
        String securityAnswer = null;
        String password = null;

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT security_answer, password FROM users WHERE username = ?";

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                securityAnswer = resultSet.getString("security_answer");
                password = resultSet.getString("password");
            }
            System.out.println("Username: " + username);
            System.out.println("Security Answer from DB: " + securityAnswer);
            System.out.println("Password from DB: " + password);

            if (securityAnswer != null && password != null && securityAnswer.equals(answer)) {
                usernameRetrieved.setText("Your password is " + password);
            }
            else {
                wrongAnswer.setText("The answer you entered is wrong, try again");
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLogoutButton(ActionEvent event) {
        try {
            // Load the Flight Search view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalcis/MainScreen.fxml"));
            Parent flightSearchParent = loader.load();
            Scene flightSearchScene = new Scene(flightSearchParent);

            // Get the stage from the event that was triggered by the button click and set the new scene
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(flightSearchScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, possibly show an error dialog to the user
        }

    }

    @FXML
    private void handleMyFlightsButtonAction(ActionEvent event) {
        try {
            // Load the "My Flights" view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MyFlightsView.fxml"));
            VBox myFlightsView = loader.load();

            // Get the current scene and set the new root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(myFlightsView);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, possibly by showing an error message to the user
        }
    }

    @FXML
    private void deleteFlightButton (ActionEvent event) {
        try {
            // Load the "My Flights" view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteFlightView.fxml"));
            VBox myFlightsView = loader.load();

            // Get the current scene and set the new root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(myFlightsView);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, possibly by showing an error message to the user
        }
    }
}