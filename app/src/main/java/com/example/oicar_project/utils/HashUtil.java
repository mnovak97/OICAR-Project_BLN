package com.example.oicar_project.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

    private static int SaltSize = 32;

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[SaltSize];
        random.nextBytes(saltBytes);
        return Base64.encodeToString(saltBytes, Base64.DEFAULT);
    }

    public static String compute_SHA256(String data, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            data = String.format("%s%s", salt, data);

            byte[] hashedData = md.digest(data.getBytes(StandardCharsets.UTF_8));
            String encodedString = Base64.encodeToString(hashedData, Base64.DEFAULT);
            return encodedString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
