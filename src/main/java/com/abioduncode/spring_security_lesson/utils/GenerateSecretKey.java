package com.abioduncode.spring_security_lesson.utils;

import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GenerateSecretKey {
  public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); // Use 256-bit key size
        SecretKey secretKey = keyGen.generateKey();
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Secret Key: " + base64Key);
    }
}
