package com.example.finalcis;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Registration {
    public TextField firstName;
    public TextField lastName;
    public TextField address;
    public TextField zipcode;
    public TextField state;
    public TextField username;
    public PasswordField password;
    public TextField email;
    public TextField ssn;
    public TextField securityQuestion;

    public void Register(ActionEvent event){
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

        // Creating a new user object using the data from the text-fields
        User user = new User(firstName, lastName, address, zipcode, state, username, password, email, ssn, securityQuestion);

    }

}
