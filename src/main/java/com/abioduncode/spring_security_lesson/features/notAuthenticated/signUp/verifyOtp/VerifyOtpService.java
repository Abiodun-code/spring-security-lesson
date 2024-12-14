package com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.verifyOtp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class VerifyOtpService {

  private final UserRepo userRepo;


  public VerifyOtpService(UserRepo userRepo){
    this.userRepo = userRepo;
  }

  public String verifyOtp(VerifyOtpDto input, String email) {

    // Check if the email exist
    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new CustomException("Email not found"));

    // Check if OTP has expired
    if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
      throw new CustomException("OTP has expired.");
    }

    // Check if OTP matches
    if (user.getOtp().equals(input.getOtp())) {
      user.setEmailVerified(true);
      user.setOtp(null); // Clear OTP after verification
      user.setOtpExpiry(null);
      userRepo.save(user);
      return "User verify successfully";
    } else {
      throw new CustomException("Invalid OTP.");
    }
  }
}
