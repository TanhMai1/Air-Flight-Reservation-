package com.example.finalcis;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Registration {
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField address;
    @FXML
    private TextField zipcode;
    @FXML
    private TextField state;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;
    @FXML
    private TextField ssn;
    @FXML
    private TextField securityQuestion;
    @FXML
    private Label failedMessageLabel;
    @FXML
    private Label successMessageLabel;

    @FXML
    private void Register(){
        User user = getUser();
        // adding the user to the database using the database access object
        UserDAO userDAO = new UserDAO();

        try {
            boolean registered = userDAO.newUser(user);
            if (registered) {
                // Registration successful
                successMessageLabel.setText("Registration Successful!");
            } else {
                // Registration failed, notify the user
                failedMessageLabel.setText("Registration failed. User not created.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User getUser() {
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String address = this.address.getText();
        String zipcode = this.zipcode.getText();
        String state = this.state.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        String email = this.email.getText();
        String ssn = this.ssn.getText();
        String securityQuestion = this.securityQuestion.getText();

        // Created a new user object using the data from the text-fields
        User user = new User(firstName, lastName, address, zipcode, state, username, password, email, ssn, securityQuestion);
        return user;
    }
}
