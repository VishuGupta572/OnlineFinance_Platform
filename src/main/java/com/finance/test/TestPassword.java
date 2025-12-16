package com.finance.test;

import com.finance.util.PasswordUtil;

public class TestPassword {
    public static void main(String[] args) {
        String password = "test_password";
        
        System.out.println("Testing Password Hashing...");
        
        // 1. Hash Password
        String hash = PasswordUtil.hashPassword(password);
        System.out.println("Original: " + password);
        System.out.println("Hash: " + hash);
        
        // 2. Verify Correct Password
        boolean match = PasswordUtil.checkPassword(password, hash);
        if (match) {
             System.out.println("PASS: Password matches hash.");
        } else {
             System.err.println("FAIL: Password did not match hash.");
        }
        
        // 3. Verify Incorrect Password
        boolean mismatch = PasswordUtil.checkPassword("wrong_password", hash);
        if (!mismatch) {
             System.out.println("PASS: Wrong password rejected.");
        } else {
             System.err.println("FAIL: Wrong password accepted.");
        }
    }
}
