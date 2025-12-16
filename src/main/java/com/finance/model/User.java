package com.finance.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private double balance;

    public User() {}

    public User(int id, String name, String email, String password, String role) {
        this(id, name, email, password, role, 0.0);
    }

    public User(int id, String name, String email, String password, String role, double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    // Profile Picture Support
    private String profilePictureBase64;
    public String getProfilePictureBase64() { return profilePictureBase64; }
    public void setProfilePictureBase64(String profilePictureBase64) { this.profilePictureBase64 = profilePictureBase64; }
}
