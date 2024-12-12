package com.abioduncode.spring_security_lesson.features.notAuthenticate.forgetPassword.verifyOtp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
public class ForgetOtpController {
  
  private final ForgetOtpService forgetOtpService;

  public ForgetOtpController(ForgetOtpService forgetOtpService){
    this.forgetOtpService = forgetOtpService;
  }

  @PostMapping("/forget-otp/{email}")
  public ResponseEntity<String> VerifyOtp(@RequestBody ForgetOtpDto forgetOtpDto, @PathVariable String email){

    String forgetMsg = forgetOtpService.verifyOtp(forgetOtpDto, email);
      return new ResponseEntity<>(forgetMsg, HttpStatus.OK);
  }
}
