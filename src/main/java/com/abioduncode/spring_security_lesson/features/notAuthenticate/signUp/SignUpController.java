package com.abioduncode.spring_security_lesson.features.notAuthenticate.signUp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.models.User;

@RestController
@RequestMapping("/auth")
public class SignUpController {

  private final SignUpService signUpService;

  public SignUpController(SignUpService signUpService){
    this.signUpService = signUpService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpDto user){

    User signUpMsg = signUpService.signUp(user);
  
    return new ResponseEntity<>(signUpMsg, HttpStatus.CREATED);
  }
}
