package com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword;

import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.changePassword.ChangePasswordDto;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.changePassword.ChangePasswordService;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.resendOtp.ForgetResendService;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.verifyOtp.ForgetOtpDto;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.verifyOtp.ForgetOtpService;
import com.abioduncode.spring_security_lesson.models.ForgetPassword;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class ForgetPasswordController {

  private final ForgetPasswordService forgetPasswordService;

  private final ForgetOtpService forgetOtpService;

  private final ChangePasswordService changePasswordService;

  private final ForgetResendService forgetResendService;

  public ForgetPasswordController(ForgetPasswordService forgetPasswordService, ForgetOtpService forgetOtpService, ForgetResendService forgetResendService, ChangePasswordService changePasswordService){
    this.forgetPasswordService = forgetPasswordService;
    this.forgetOtpService = forgetOtpService;
    this.forgetResendService = forgetResendService;
    this.changePasswordService = changePasswordService;
  }
  
  @PostMapping("/forget-password")
  public ResponseEntity<?> generateOtp(@RequestBody ForgetPasswordDto forgetPasswordDto) {

    ForgetPassword forgetMsg = forgetPasswordService.generateOtp(forgetPasswordDto);
    
    return new ResponseEntity<>(forgetMsg, HttpStatus.OK);
  }

  @PostMapping("/forget-otp/{email}")
  public ResponseEntity<String> VerifyOtp(@RequestBody ForgetOtpDto forgetOtpDto, @PathVariable String email){

    String forgetMsg = forgetOtpService.verifyOtp(forgetOtpDto, email);
    
    return new ResponseEntity<>(forgetMsg, HttpStatus.OK);
  }

  @PostMapping("/forget-resend/{email}")
  public ResponseEntity<?> ResendOtp(@PathVariable String email){
    
    ForgetPassword forgetMsg = forgetResendService.resendOtp(email);

    return new ResponseEntity<>(forgetMsg, HttpStatus.OK);
  }

  @PostMapping("/forget-change/{email}")
  public ResponseEntity<?> ChangePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable String email){
    
    String changeMsg = changePasswordService.changePassword(changePasswordDto, email);

    return new ResponseEntity<>(changeMsg, HttpStatus.OK);
  }
  
}
