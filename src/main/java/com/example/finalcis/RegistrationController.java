package com.example.finalcis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.EventObject;

public class RegistrationController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField zipField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ssnField;

    @FXML
    private TextField securityQuestionField;

    @FXML
    private void registerButtonAction() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String zip = zipField.getText();
        String state = stateField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String ssn = ssnField.getText();
        String securityQuestion = securityQuestionField.getText();

        registerdUser(firstName, lastName, address, zip, state, username, password, email, ssn, securityQuestion);
        System.out.println("registration successful");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent root = loader.load();

       Stage stage = new Stage();
       stage.setScene(new Scene(root)) ;
       stage.show();

    }

    private void registerdUser(String firstName, String lastName, String address, String zip, String state, String username, String password, String email, String ssn, String securityQuestion) {
        String url = "jdbc:mysql://localhost:3306/authentication_system";
        String user = "root";
        String dbpassword = "Nicasio/123";
        String INSERT_QUERY = "INSERT INTO users (firstName,lastName,address, zip, state, username, password, email, ssn, securityQuestion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, dbpassword);
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, zip);
                preparedStatement.setString(5, state);
                preparedStatement.setString(6, username);
                preparedStatement.setString(7, password);
                preparedStatement.setString(8, email);
                preparedStatement.setString(9, ssn);
                preparedStatement.setString(10, securityQuestion);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
