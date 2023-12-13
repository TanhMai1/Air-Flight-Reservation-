package com.example.finalcis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class UserDAO {
    public DBConnection dbConnection;
    public UserDAO() {
        dbConnection = new DBConnection();
    }

    public boolean newUser(User user){
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO users (first_name, last_name, address, zipcode, state, username, password, email, ssn, security_question) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getAddress());
            preparedStatement.setString(4,user.getZipcode());
            preparedStatement.setString(5,user.getState());
            preparedStatement.setString(6,user.getUsername());
            preparedStatement.setString(7,user.getPassword());
            preparedStatement.setString(8,user.getEmail());
            preparedStatement.setString(9,user.getSsn());
            preparedStatement.setString(10,user.getSecurityQuestion());

            int userCreated = preparedStatement.executeUpdate();

            return userCreated > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
