package com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.verifyOtp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.ForgetPassword;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.ForgetPasswordRepo;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class ForgetOtpService {
  
  private final UserRepo userRepo;

  private final ForgetPasswordRepo forgetPasswordRepo;

  public ForgetOtpService(UserRepo userRepo, ForgetPasswordRepo forgetPasswordRepo){
    this.userRepo = userRepo;
    this.forgetPasswordRepo = forgetPasswordRepo;
  }

  public String verifyOtp(ForgetOtpDto forgetOtpDto, String email){

    // Check if email exist
    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new CustomException("Email not find"));

    // Check if a ForgetPassword record already exists for this user
    ForgetPassword forgetPassword = user.getForgetPassword();

    // Check if OTP has expired
    if (forgetPassword.getOtpExpiry().isBefore(LocalDateTime.now())) {
      throw new CustomException("OTP has expired.");
    }

    if (forgetPassword.getOtp().equals(forgetOtpDto.getOtp())) {
      forgetPassword.setEmailVerified(true);
      forgetPassword.setOtp(null);
      forgetPassword.setOtpExpiry(null);
      forgetPasswordRepo.save(forgetPassword);
      return "User verify successfully";
    }else{
      throw new CustomException("Invalid OTP.");
    }
  }
}
