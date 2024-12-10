package com.abioduncode.spring_security_lesson.utils;

import java.security.SecureRandom;

public class OTPGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6; // You can adjust the length as needed
    private static final String NUMERIC_POOL = "0123456789";

    public static Integer generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(NUMERIC_POOL.length());
            otp.append(NUMERIC_POOL.charAt(index));
        }
        return Integer.parseInt(otp.toString());
    }
}
