package com.abioduncode.spring_security_lesson.services;

import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
    try {
        Optional<User> userOptional = userRepo.findByEmail(forgetEmailDto.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Create a new ForgetPassword entity
            ForgetPassword forgetPassword = new ForgetPassword();

            // Generate OTP
            Integer otp = generateOTP();
            forgetPassword.setOtp(otp);  // Set OTP

            // Set OTP expiry (5 minutes from now)
            forgetPassword.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

            // Set the bi-directional relationship
            forgetPassword.setUser(user);
            user.setForgetPassword(forgetPassword);

            // Save the ForgetPassword entity
            forgetPasswordRepo.save(forgetPassword);

            // Save the User entity to persist the forget_password_fpid column
            userRepo.save(user);

            return "Otp sent successfully";
        } else {
            throw new CustomException("User not found.");
        }
    } catch (Exception e) {
        // Wrap the exception in your custom exception
        throw new CustomException("Error occurred while generating OTP: " + e.getMessage());
    }
}


}
