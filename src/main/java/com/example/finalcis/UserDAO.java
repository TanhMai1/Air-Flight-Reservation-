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
            preparedStatement = connection.prepareStatement("INSERT INTO users (id, first_name, last_name, address, zipcode, state, username, password, email, ssn, security_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2,user.getFirstName());
            preparedStatement.setString(3,user.getLastName());
            preparedStatement.setString(4,user.getAddress());
            preparedStatement.setString(5,user.getZipcode());
            preparedStatement.setString(6,user.getState());
            preparedStatement.setString(7,user.getUsername());
            preparedStatement.setString(8,user.getPassword());
            preparedStatement.setString(9,user.getEmail());
            preparedStatement.setString(10,user.getSsn());
            preparedStatement.setString(11,user.getSecurityAnswer());


            int userCreated = preparedStatement.executeUpdate();

            return userCreated > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
