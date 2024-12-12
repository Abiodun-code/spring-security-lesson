package com.abioduncode.spring_security_lesson.features.notAuthenticate.forgetPassword.ChangePassword;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.ForgetPassword;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.ForgetPasswordRepo;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class ChangePasswordService {
  
  private final UserRepo userRepo;

  private final ForgetPasswordRepo forgetPasswordRepo;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public ChangePasswordService(UserRepo userRepo, ForgetPasswordRepo forgetPasswordRepo){
    this.userRepo = userRepo;
    this.forgetPasswordRepo = forgetPasswordRepo;
  }

  public String changePassword(ChangePasswordDto changePasswordDto, String email){

    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new CustomException("Email not found"));

    ForgetPassword forgetPassword = user.getForgetPassword();

    if (!Objects.equals(changePasswordDto.getNewPassword(), changePasswordDto.getConfirmPassword())){
      throw  new CustomException("Password not match");
    }
    
    String passwordEncoder = encoder.encode(changePasswordDto.getNewPassword());
    user.setPassword(passwordEncoder);
    userRepo.save(user);

    // Clear ForgetPassword record
    forgetPassword.setEmailVerified(false);
    forgetPassword.setOtp(null);
    forgetPassword.setOtpExpiry(null);
    forgetPasswordRepo.save(forgetPassword);
    return "Password change successfully";
    
  }
}
