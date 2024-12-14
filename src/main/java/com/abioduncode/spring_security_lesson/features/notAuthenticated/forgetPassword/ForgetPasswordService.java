package com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword;

import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.ForgetPassword;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.ForgetPasswordRepo;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class ForgetPasswordService {
  
  private final UserRepo userRepo;

  private final ForgetPasswordRepo forgetPasswordRepo;

  public ForgetPasswordService(UserRepo userRepo, ForgetPasswordRepo forgetPasswordRepo){
    this.userRepo = userRepo;
    this.forgetPasswordRepo = forgetPasswordRepo;

  }

  public ForgetPassword generateOtp(ForgetPasswordDto forgetPasswordDto) {
    // Fetch user by email
    User user = userRepo.findByEmail(forgetPasswordDto.getEmail())
        .orElseThrow(() -> new CustomException("Email not found"));

    if (!user.isEmailVerified()) {
        throw new CustomException("Verify your email first.");
    }

    // Ensure the User is managed
    user = userRepo.findById(user.getId())
        .orElseThrow(() -> new CustomException("User not found"));

    // Create or update ForgetPassword
    ForgetPassword forgetPassword = user.getForgetPassword();

    if (forgetPassword == null) {
        forgetPassword = new ForgetPassword();
        forgetPassword.setUser(user); // Ensure the relationship
        user.setForgetPassword(forgetPassword);
    }

    // Generate OTP and set expiry
    forgetPassword.setOtp(generateOTP());
    forgetPassword.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
    forgetPassword.setEmailVerified(false);

    // Save User and ForgetPassword
    return userRepo.save(user).getForgetPassword();
  }


}
