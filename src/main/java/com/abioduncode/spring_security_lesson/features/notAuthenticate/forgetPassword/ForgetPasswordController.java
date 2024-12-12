package com.abioduncode.spring_security_lesson.features.notAuthenticate.forgetPassword;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class ForgetPasswordController {

  private final ForgetPasswordService forgetPasswordService;

  public ForgetPasswordController(ForgetPasswordService forgetPasswordService){
    this.forgetPasswordService = forgetPasswordService;
  }
  
  @PostMapping("/forget-password")
  public ResponseEntity<?> generateOtp(@RequestBody ForgetPasswordDto forgetPasswordDto) {

    String forgetMsg = forgetPasswordService.generateOtp(forgetPasswordDto);
    return new ResponseEntity<>(forgetMsg, HttpStatus.OK);
  }
  
}
