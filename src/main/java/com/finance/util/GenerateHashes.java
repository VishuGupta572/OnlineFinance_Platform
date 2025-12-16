package com.finance.util;

public class GenerateHashes {
    public static void main(String[] args) {
        String[] passwords = {"admin123", "advisor123", "user123"};
        for (String pw : passwords) {
            String hash = PasswordUtil.hashPassword(pw);
            System.out.println("Password: " + pw + " -> Hash: " + hash);
        }
    }
}
