package com.abioduncode.spring_security_lesson.utils;

import java.security.SecureRandom;

public class OTPGenerator {

    private static final SecureRandom random = new SecureRandom();

    private static final int OTP_LENGTH = 6; // You can adjust the length as needed
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            otp.append(CHAR_POOL.charAt(index));
        }
        return otp.toString();
    }
}
