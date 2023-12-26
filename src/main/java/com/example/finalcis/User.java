package com.example.finalcis;

public class User {
    private String firstName;
    private String lastName;
    private String address;
    private String zipcode;
    private String state;
    private String username;
    private String password;
    private String email;
    private String ssn;
    private String securityAnswer;
    private int id;


    public User(int id, String firstName, String lastName, String address, String zipcode,
                String state, String username, String password, String email,
                String ssn, String securityAnswer) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipcode = zipcode;
        this.state = state;
        this.username = username;
        this.password = password; // In real-world scenarios, this should be hashed
        this.email = email;
        this.ssn = ssn;
        this.securityAnswer = securityAnswer;
    }
    public User(int id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {return lastName;}


    public String getAddress() {
        return address;
    }


    public String getZipcode() {
        return zipcode;
    }

    public void setZip(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }


    public String getSsn() {
        return ssn;
    }


    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}