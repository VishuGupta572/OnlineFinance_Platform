package com.finance.model;

public class Budget {
    private int id;
    private int userId;
    private String category;
    private double limitAmount;

    public Budget() {}

    public Budget(int id, int userId, String category, double limitAmount) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.limitAmount = limitAmount;
    }

    public Budget(int userId, String category, double limitAmount) {
        this.userId = userId;
        this.category = category;
        this.limitAmount = limitAmount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getLimitAmount() { return limitAmount; }
    public void setLimitAmount(double limitAmount) { this.limitAmount = limitAmount; }
}
