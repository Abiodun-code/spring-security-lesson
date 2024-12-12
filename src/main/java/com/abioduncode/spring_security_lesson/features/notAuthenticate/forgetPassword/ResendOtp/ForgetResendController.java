package com.abioduncode.spring_security_lesson.features.notAuthenticate.forgetPassword.ResendOtp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.models.ForgetPassword;

@RestController
@RequestMapping("/auth")
public class ForgetResendController {
  
  private final ForgetResendService forgetResendService;

  public ForgetResendController(ForgetResendService forgetResendService){
    this.forgetResendService = forgetResendService;
  }
  
  @PostMapping("/forget-resend/{email}")
  public ResponseEntity<?> ResendOtp(@PathVariable String email){
    
    ForgetPassword forgetMsg = forgetResendService.resendOtp(email);

    return new ResponseEntity<>(forgetMsg, HttpStatus.OK);
  }
}
