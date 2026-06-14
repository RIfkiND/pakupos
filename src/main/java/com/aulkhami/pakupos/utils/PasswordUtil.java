package com.aulkhami.pakupos.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(
        String inputPassword,
        String storedHash
    ) {
        String inputHash = hashPassword(inputPassword);
        return inputHash.equals(storedHash);
    }

    /**
     * @deprecated Salt is not explicitly stored in the current users table schema.
     */
    @Deprecated
    public static String hashPassword(String password, String salt) {
        return hashPassword(password + salt);
    }

    /**
     * @deprecated Salt is not explicitly stored in the current users table schema.
     */
    @Deprecated
    public static boolean verifyPassword(
        String inputPassword,
        String storedHash,
        String salt
    ) {
        return verifyPassword(inputPassword + salt, storedHash);
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
