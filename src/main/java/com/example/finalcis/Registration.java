package com.example.finalcis;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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
    private TextField securityAnswer;
    @FXML
    private Label failedMessageLabel;
    @FXML
    private Label successMessageLabel;
    private int id;

    @FXML
    private void Register() {
        // creating a user with the createUser Method
        User user = createUser();
        //creating an instance of the userDAO to add the user to the database
        UserDAO userDAO = new UserDAO();

        try {
            // adding the user to the database
            boolean registered = userDAO.newUser(user);
            if (registered) {
                // Registration successful
                successMessageLabel.setText("Registration Successful!");
            } else {
                // Registration failed, notify the user
                failedMessageLabel.setText("Registration failed. User not created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private User createUser() {
        int id = this.id;
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String address = this.address.getText();
        String zipcode = this.zipcode.getText();
        String state = this.state.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        String email = this.email.getText();
        String ssn = this.ssn.getText();
        String securityAnswer = this.securityAnswer.getText();


        // Created a new user object using the data from the text-fields
        return new User(id, firstName, lastName, address, zipcode, state, username, password, email, ssn, securityAnswer);
    }

    @FXML
    public Button closeButton;

    @FXML
    public void handleCloseButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent root = loader.load();

        Stage LoginStage = new Stage();
        LoginStage.setScene(new Scene(root));
        LoginStage.show();


        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
