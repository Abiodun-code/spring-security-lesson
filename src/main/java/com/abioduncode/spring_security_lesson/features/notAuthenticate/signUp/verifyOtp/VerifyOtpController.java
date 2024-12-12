package com.abioduncode.spring_security_lesson.features.notAuthenticate.signUp.verifyOtp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class VerifyOtpController {
  
  private final VerifyOtpService verifyOtpService;

  public VerifyOtpController(VerifyOtpService verifyOtpService){
    this.verifyOtpService = verifyOtpService;
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<?> verifyEmailWithOtp(@RequestBody VerifyOtpDto verifyOtpDto) {

    String verifyMsg = verifyOtpService.verifyEmailWithOtp(verifyOtpDto);
    
    return new ResponseEntity<>(verifyMsg, HttpStatus.OK);
  }
}
