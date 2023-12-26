package com.example.finalcis;

public class SessionManager {
    private static final SessionManager instance = new SessionManager();
    private Integer userId;

    private SessionManager() {}

    public static SessionManager getInstance() {
        return instance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}

