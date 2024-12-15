package com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.lastNameUpdate;

import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class LastUpdateService {
  
  private final UserRepo userRepo;

  public LastUpdateService(UserRepo userRepo){
    this.userRepo = userRepo;
  }

  public User lastNameUpdate(LastUpdateDto lastUpdateDto, String email){

    // Generate OTP
    Integer otp = generateOTP();

    User user = userRepo.findByEmail(email)
    .orElseThrow(() -> new CustomException("Email not found"));

    if(user != null){
      user.setLastName(lastUpdateDto.getLastName());
      user.setOtp(otp);
      user.setEmailVerified(false);
      user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
      return userRepo.save(user);
    }   
    throw new CustomException("User not found.");
  }
}
