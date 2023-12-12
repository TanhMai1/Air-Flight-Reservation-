package com.example.finalcis;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField zipcodeField;

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
    private void registerButtonAction(){
        /*
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String zipcode = zipcodeField.getText();
        String state = stateField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String ssn = ssnField.getText();
        String securityQuestion = securityQuestionField.getText();

        System.out.println("Registration Details:");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Address: " + address);
        System.out.println("Zipcode: " + zipcode);
        System.out.println("State: " + state);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("SSN: " + ssn);
        System.out.println("Security Question: " + securityQuestion);
         */
    }
}
