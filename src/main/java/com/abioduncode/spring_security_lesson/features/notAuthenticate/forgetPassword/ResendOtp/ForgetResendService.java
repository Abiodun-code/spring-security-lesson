package com.abioduncode.spring_security_lesson.features.notAuthenticate.forgetPassword.ResendOtp;

import static com.abioduncode.spring_security_lesson.utils.OTPGenerator.generateOTP;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.ForgetPassword;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.ForgetPasswordRepo;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class ForgetResendService {

  private final ForgetPasswordRepo forgetPasswordRepo;

  private final UserRepo userRepo;

  public ForgetResendService(ForgetPasswordRepo forgetPasswordrepo, UserRepo userRepo){
    this.forgetPasswordRepo = forgetPasswordrepo;
    this.userRepo = userRepo;
  }
  
  public ForgetPassword resendOtp(String email){

    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new CustomException("Email not find"));

    // Check if a ForgetPassword record already exists for this user
    ForgetPassword forgetPassword = user.getForgetPassword();

    //  if (forgetPassword == null) {
    //     // Create a new ForgetPassword entity
    //     forgetPassword = new ForgetPassword();
    //     forgetPassword.setUser(user);
    // }

    // Generate new OTP and update the user
    Integer otp = generateOTP();
    forgetPassword.setOtp(otp);

    // Set OTP expiry (10 minutes from now)
    forgetPassword.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

    // Send verification email
    // sendVerifyEmail(user);

    return forgetPasswordRepo.save(forgetPassword);

  }
}
