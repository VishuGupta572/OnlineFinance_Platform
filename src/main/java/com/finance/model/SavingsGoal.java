package com.finance.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class SavingsGoal {
    private int id;
    private int userId;
    private String name;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private Date deadline;
    private Timestamp createdAt;

    public SavingsGoal() {}

    public SavingsGoal(int userId, String name, BigDecimal targetAmount, BigDecimal currentAmount, Date deadline) {
        this.userId = userId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    // Helper for progress calculation
    public double getProgressPercentage() {
        if (targetAmount == null || targetAmount.compareTo(BigDecimal.ZERO) == 0) return 0;
        return currentAmount.divide(targetAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
    }
}
