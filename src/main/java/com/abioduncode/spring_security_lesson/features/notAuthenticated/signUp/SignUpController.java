package com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.firstNameUpdate.FirstUpdateDto;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.firstNameUpdate.FirstUpdateService;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.lastNameUpdate.LastUpdateDto;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.lastNameUpdate.LastUpdateService;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.resendOtp.ResendOtpService;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.verifyOtp.VerifyOtpDto;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.verifyOtp.VerifyOtpService;
import com.abioduncode.spring_security_lesson.models.User;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class SignUpController {

  private final SignUpService signUpService;

  private final VerifyOtpService verifyOtpService;

  private final ResendOtpService resendOtpService;

  private final FirstUpdateService firstUpdateService;

  private final LastUpdateService lastUpdateService;

  public SignUpController(SignUpService signUpService,VerifyOtpService verifyOtpService,ResendOtpService resendOtpService, FirstUpdateService firstUpdateService,LastUpdateService lastUpdateService){
    this.signUpService = signUpService;
    this.verifyOtpService = verifyOtpService;
    this.resendOtpService = resendOtpService;
    this.firstUpdateService = firstUpdateService;
    this.lastUpdateService = lastUpdateService;
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


  @PutMapping("/first-update/{email}")
  public ResponseEntity<?> firstUpdate(@RequestBody FirstUpdateDto firstUpdateDto, @PathVariable String email){

    User firstUpdateMsg = firstUpdateService.firstNameUpdate(firstUpdateDto, email);
    
    return new ResponseEntity<>(firstUpdateMsg, HttpStatus.OK);
  }

  @PutMapping("/last-update/{email}")
  public ResponseEntity<?> lastUpdate(@RequestBody LastUpdateDto lastUpdateDto, @PathVariable String email){

    User lastUpdateMsg = lastUpdateService.lastNameUpdate(lastUpdateDto, email);
    
    return new ResponseEntity<>(lastUpdateMsg, HttpStatus.OK);
  }
}
