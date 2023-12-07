package com.example.finalcis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    protected void handleLoginButtonAction(ActionEvent event) {
        try {
            if (authenticate(usernameField.getText(), passwordField.getText())) {
                System.out.println("Login successful!");

                // Load the "Hello" view
                Parent loginScreenParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/finalcis/LoginScreen.fxml")));
                Scene loginFormScene = new Scene(loginScreenParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(loginFormScene);
                window.show();
            } else {
                System.out.println("Login failed. Incorrect username or password.");
                loginMessageLabel.setText("Incorrect username or password."); // Update UI with error message
                passwordField.clear(); // Optionally clear the password field
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading the Hello screen.");
            e.printStackTrace();
        }
    }


    private boolean authenticate(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If the result set is not empty, authentication succeeded
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // It's important to close the resources in the finally block to ensure they are closed even if an exception is thrown.
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}