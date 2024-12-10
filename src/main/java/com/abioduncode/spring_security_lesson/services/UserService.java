package com.abioduncode.spring_security_lesson.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.dto.RegisterDto;
import com.abioduncode.spring_security_lesson.dto.ResendDto;
import com.abioduncode.spring_security_lesson.dto.VerifyUserDto;
import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

  public List<User>allUsers(){
    List<User> users = new ArrayList<>();
    userRepo.findAll().forEach(users::add);
    return users;
  }

  public User register(RegisterDto registerDto) {

    try {

      // Generate OTP
      Integer otp = generateOTP();

      // Check if the email exist
      Optional<User> userOptional = userRepo.findByEmail(registerDto.getEmail());

      // If email exist you have to use another email
      if (userOptional.isPresent()) {
        throw new CustomException("Email already exist.");
      }

      // Create new user
      User user = new User(
        registerDto.getId(),
        registerDto.getFirstName(),
        registerDto.getLastName(),
        registerDto.getEmail(),
        encoder.encode(registerDto.getPassword())
      );

      // Set additional fields for the user
      user.setOtp(otp);
      user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
      user.setEmailVerified(false);

      // Send verification email
      // sendVerifyEmail(user);

      // Save and return the user
      return userRepo.save(user);
    } catch (Exception e) {
        throw new CustomException("" + e.getMessage());
    }
  }


  public String verifyUser(VerifyUserDto input) {

    try {
      // Check if the email exist
      Optional<User> userOptional = userRepo.findByEmail(input.getEmail());

      // If email exist it will return the otp
      if (userOptional.isPresent()) {

        User user = userOptional.get();

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
      } else {
        throw new CustomException("User not found.");
      }
    } catch (Exception e) {
      throw new CustomException("" + e.getMessage());
    }
  }

  public User resendVerifyCode(ResendDto resendDto) {

    try {

      // Check if the email exists
      Optional<User> optionUser = userRepo.findByEmail(resendDto.getEmail());

      if (optionUser.isPresent()) {

        User user = optionUser.get();

        // Check if the email is already verified
        if (user.isEmailVerified()) {
          throw new CustomException("Email is already verified.");
        }

        // Generate new OTP and update the user
        Integer otp = generateOTP();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

        // Send verification email
        // sendVerifyEmail(user);

        // Save user
        return userRepo.save(user);

        // return "Verification code resent successfully.";
      }else{
        throw new CustomException("Email not found.");
      }
      
    } catch (Exception e) {
      throw new CustomException("Error: " + e.getMessage());
    }
}


  public void sendVerifyEmail(User user) {
      String subject = "Account Verification";
      Integer otps =  user.getOtp();
      String htmlMessage = "<html>"
      +"<body style=\"font-family: Arial, sans-serif;\">"
      +"<div style=\"backgroung-color:#f5f5f5; padding:20px\">"
      +"<h1 style=\"color:#333;\">Welcome to our app</h1>"
      +"<p style=\"font-size:16px\">Please enter the verification code below to cotinue</p>"
      +"<div style=\"background-color:#fff; padding:20px; border-radius:5px; box-shadow:0 0 10px rgba(0,0,0,0.1);\">"
      +"<h3 style=\"color:#333;\">Verification code:</h3>"
      +"<p style=\"font-size:18px; color:#007bff; font-weight:bold;\">" + otps + "</p>"
      +"</div>"
      +"</div>"
      +"</body>"
      +"</html>";

      try {
        emailService.sendEmail(user.getEmail(), subject, htmlMessage);
      } catch (Exception e) {
        throw new CustomException("Send email cant send " + e.getMessage());
      }
  }
}
