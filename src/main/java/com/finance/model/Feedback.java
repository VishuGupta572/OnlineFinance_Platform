package com.finance.model;

import java.sql.Timestamp;

public class Feedback {
    private int id;
    private int userId;
    private String message;
    private Timestamp createdAt;
    private String userName; // For display purposes

    public Feedback() {}

    public Feedback(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public Feedback(int id, int userId, String message, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
