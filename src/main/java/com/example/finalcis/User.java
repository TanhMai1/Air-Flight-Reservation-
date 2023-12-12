package com.example.finalcis;

public class User {
    private String firstName;
    private String lastName;
    private String address;
    private String zip;
    private String state;
    private String username;
    private String password;
    private String email;
    private String ssn;
    private String securityQuestion;
    private String securityAnswer;
    private int id;


    public User(String firstName, String lastName, String address, String zip,
                String state, String username, String password, String email,
                String ssn, String securityQuestion, String securityAnswer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.state = state;
        this.username = username;
        this.password = password; // In real-world scenarios, this should be hashed
        this.email = email;
        this.ssn = ssn;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;// Consider the security implications of storing SSNs
    }
    public User(int id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }


    public String getFirstNameirstName() {
        return firstName;
    }

    public void setFirstName(String FirstName) {
        this.firstName = firstName;
    }

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
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
}