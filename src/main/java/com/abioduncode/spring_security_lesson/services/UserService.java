package com.abioduncode.spring_security_lesson.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.dto.VerifyUserDto;
import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  private final UserRepo userRepo;

  private final EmailService emailService;

  public UserService(UserRepo userRepo, EmailService emailService){
    this.userRepo = userRepo;
    this.emailService = emailService;
  }

  public User register(User user){
    try{

      String otp = generateOTP();

      Optional<User> userOptional = userRepo.findByEmail(user.getEmail());
      if (userOptional.isPresent()) {
        throw new CustomException("Email is already in use. Please use a different email.");
      }
      user.setPassword(encoder.encode(user.getPassword()));
      user.setOtp(otp);
      user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
      user.setEmailVerified(false);
      sendVerifyEmail(user);
      return userRepo.save(user);
    }catch(Exception e){
      throw new CustomException("Failed to register user" + e.getMessage());
    }
  }

  public String verifyUser(VerifyUserDto input) {
    Optional<User> userOptional = userRepo.findByEmail(input.getEmail());

    if (userOptional.isPresent()) {
        User user = userOptional.get();

        // Check if OTP has expired
        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired.");
        }

        // Check if OTP matches
        if (user.getOtp().equals(input.getOtp())) {
            user.setEmailVerified(true);
            user.setOtp(null); // Clear OTP after verification
            user.setOtpExpiry(null);
            userRepo.save(user);
            return "User verify successfully";
        } else {
            throw new RuntimeException("Invalid OTP.");
        }
    } else {
        throw new RuntimeException("User not found.");
    }
  }



  public void sendVerifyEmail(User user) {
      // TODO Auto-generated method stub
      String subject = "Account Verification";
      String otps =  user.getOtp();
      String htmlMessage = "<html>"
      +"<body style=\"font-family: Arial, sans-serif;\">"
      +"<div style=\"backgroung-color:#f5f5f5; padding:20px\">"
      +"<h1 style=\"color:#333;\">Welcome to our app</h1>"
      +"<p style=\"font-size:16px\">Please enter the verification code below to cotinue</p>"
      +"<div>"
      +"<h3 style=\"color:#333;\">Verification code:</h3>"
      +"<p style=\"font-size:18px; color:#007bff; font-weight:bold;\">" + otps + "</p>"
      +"</div>"
      +"</div>"
      +"</body>"
      +"</html>";

      try {
        emailService.sendEmail(user.getEmail(), subject, htmlMessage);
      } catch (Exception e) {
        // TODO: handle exception
        throw new CustomException("Send email cant send " + e.getMessage());
      }
  }
}
