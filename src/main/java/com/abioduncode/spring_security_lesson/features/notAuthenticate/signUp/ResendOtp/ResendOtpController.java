package com.abioduncode.spring_security_lesson.features.notAuthenticate.signUp.ResendOtp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.models.User;

@RestController
@RequestMapping("/auth")
public class ResendOtpController {

  private final ResendOtpService resendOtpService;

  public ResendOtpController(ResendOtpService resendOtpService){

    this.resendOtpService = resendOtpService;
  }
  
  @PostMapping("/resend-otp")
  public ResponseEntity<?> resendOtpIfExpire(@RequestBody ResendOtpDto resendOtpDto ){

    User resendMsg = resendOtpService.resendOtpIfExpire(resendOtpDto);

    return new ResponseEntity<>(resendMsg, HttpStatus.CREATED);
  }
}
