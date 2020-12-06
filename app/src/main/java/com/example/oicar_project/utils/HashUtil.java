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

    public static String computeHMAC_SHA256(String data, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.decode(salt, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPassword = md.digest(data.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(hashedPassword, Base64.DEFAULT);
    }
}
