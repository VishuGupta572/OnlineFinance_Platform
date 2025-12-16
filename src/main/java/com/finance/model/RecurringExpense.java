package com.finance.model;

public class RecurringExpense {
    private int id;
    private int userId;
    private String category;
    private double amount;
    private String description;
    private boolean active;

    public RecurringExpense() {}

    public RecurringExpense(int userId, String category, double amount, String description) {
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.active = true;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
