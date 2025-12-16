package com.finance.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Define the workload factor (log rounds) - 12 is a good balance between security and performance
    private static final int LOG_ROUNDS = 12;

    /**
     * Hash a password using BCrypt.
     * @param plainTextPassword The plain text password.
     * @return The hashed password.
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Check if a plain text password matches a stored hash.
     * @param plainTextPassword The plain text password.
     * @param storedHash The stored hashed password.
     * @return true if they match, false otherwise.
     */
    public static boolean checkPassword(String plainTextPassword, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("$2a$"))
            throw new IllegalArgumentException("Invalid hash provided for comparison");

        return BCrypt.checkpw(plainTextPassword, storedHash);
    }
}
