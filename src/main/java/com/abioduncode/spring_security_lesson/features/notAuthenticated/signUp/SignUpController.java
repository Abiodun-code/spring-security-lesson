package com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.ResendOtp.ResendOtpService;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.verifyOtp.VerifyOtpDto;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.verifyOtp.VerifyOtpService;
import com.abioduncode.spring_security_lesson.models.User;

@RestController
@RequestMapping("/auth")
public class SignUpController {

  private final SignUpService signUpService;

  private final VerifyOtpService verifyOtpService;

  private final ResendOtpService resendOtpService;

  public SignUpController(SignUpService signUpService,VerifyOtpService verifyOtpService,ResendOtpService resendOtpService){
    this.signUpService = signUpService;
    this.verifyOtpService = verifyOtpService;
    this.resendOtpService = resendOtpService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpDto user){

    User signUpMsg = signUpService.signUp(user);
  
    return new ResponseEntity<>(signUpMsg, HttpStatus.CREATED);
  }

  @PostMapping("/verify-otp/{email}")
  public ResponseEntity<?> verifyEmailWithOtp(@RequestBody VerifyOtpDto verifyOtpDto, @PathVariable String email) {

    String verifyMsg = verifyOtpService.verifyOtp(verifyOtpDto, email);
    
    return new ResponseEntity<>(verifyMsg, HttpStatus.OK);
  }

  @PostMapping("/resend-otp/{email}")
  public ResponseEntity<?> resendOtpIfExpire(@PathVariable String email ){

    User resendMsg = resendOtpService.resendOtp(email);

    return new ResponseEntity<>(resendMsg, HttpStatus.CREATED);
  }
}
