package com.abioduncode.spring_security_lesson.features.notAuthenticate.signUp;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.features.email.EmailService;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;
import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

@Service
public class SignUpService {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  private final UserRepo userRepo;

  private final EmailService emailService;

  public SignUpService(UserRepo userRepo, EmailService emailService){
    this.userRepo = userRepo;
    this.emailService = emailService;
  }

  public User signUp(SignUpDto signUpDto) {

    // Generate OTP
    Integer otp = generateOTP();

    // Check if the email exist
    boolean existEmail = userRepo.findByEmail(signUpDto.getEmail()).isPresent();

    // If email exist you have to use another email
    if (existEmail) {
      throw new CustomException("Email already exist.");
    }

    // Create and populate a new user
    User user = new User();

    user.setFirstName(signUpDto.getFirstName());
    user.setLastName(signUpDto.getLastName());
    user.setEmail(signUpDto.getEmail());
    user.setPassword(encoder.encode(signUpDto.getPassword()));
    user.setOtp(otp);
    user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
    user.setEmailVerified(false);

    // Send verification email
    // sendVerifyEmail(user);

    // Save and return the user
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
