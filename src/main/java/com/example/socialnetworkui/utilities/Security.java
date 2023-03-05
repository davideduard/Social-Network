package com.example.socialnetworkui.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

    public String encrypt(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(string.getBytes());
        return byteToHex(hash);
    }

    private String byteToHex(byte[] bytes){
        StringBuilder result = new StringBuilder();
        for (byte b : bytes){
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }

}
