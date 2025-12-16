package com.finance.model;

import java.sql.Timestamp;

public class Advice {
    private int id;
    private int advisorId;
    private int userId;
    private String message;
    private Timestamp createdAt;

    public Advice() {}

    public Advice(int id, int advisorId, int userId, String message, Timestamp createdAt) {
        this.id = id;
        this.advisorId = advisorId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Advice(int advisorId, int userId, String message) {
        this.advisorId = advisorId;
        this.userId = userId;
        this.message = message;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAdvisorId() { return advisorId; }
    public void setAdvisorId(int advisorId) { this.advisorId = advisorId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
