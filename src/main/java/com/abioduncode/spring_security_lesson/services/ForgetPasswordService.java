package com.abioduncode.spring_security_lesson.services;

import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.dto.ForgetEmailDto;
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

  public String generateOtp(ForgetEmailDto forgetEmailDto) {

    // Fetch user by email
    User user = userRepo.findByEmail(forgetEmailDto.getEmail())
    .orElseThrow(() -> new CustomException("Email not found"));

    if (!user.isEmailVerified()) {
     throw new CustomException("Verify your email first.");
    }

    // Check if a ForgetPassword record already exists for this user
    ForgetPassword forgetPassword = user.getForgetPassword();

    if (forgetPassword == null) {
        // Create a new ForgetPassword entity
        forgetPassword = new ForgetPassword();
        forgetPassword.setUser(user);
    }

    // Generate OTP
    Integer otp = generateOTP();
    forgetPassword.setOtp(otp);

    // Set OTP expiry (10 minutes from now)
    forgetPassword.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

    // Save the ForgetPassword entity
    forgetPasswordRepo.save(forgetPassword);

    return "Otp sent successfully";
  }


}
