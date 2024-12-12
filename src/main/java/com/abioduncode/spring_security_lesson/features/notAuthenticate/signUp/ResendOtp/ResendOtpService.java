package com.abioduncode.spring_security_lesson.features.notAuthenticate.signUp.ResendOtp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.features.email.EmailService;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

@Service
public class ResendOtpService {
  
  private final UserRepo userRepo;

  private final EmailService emailService;

  public ResendOtpService(UserRepo userRepo, EmailService emailService){
    this.userRepo = userRepo;
    this.emailService = emailService;
  }

  public User resendOtpIfExpire(ResendOtpDto resendOtpDto) {
    
    // Check if the email exists
    User user = userRepo.findByEmail(resendOtpDto.getEmail())
    .orElseThrow(()-> new CustomException("Email not found"));

    if(user.isEmailVerified()) {
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
