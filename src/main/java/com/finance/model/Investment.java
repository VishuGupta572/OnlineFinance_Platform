package com.finance.model;

public class Investment {
    private int id;
    private int userId;
    private String symbol;
    private double quantity;
    private double avgBuyPrice;

    // Transient fields for display
    private double currentPrice;
    private double currentValue;

    public Investment() {}

    public Investment(int userId, String symbol, double quantity, double avgBuyPrice) {
        this.userId = userId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getAvgBuyPrice() { return avgBuyPrice; }
    public void setAvgBuyPrice(double avgBuyPrice) { this.avgBuyPrice = avgBuyPrice; }

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }
    
    public double getProfitLoss() {
        return currentValue - (quantity * avgBuyPrice);
    }
}
