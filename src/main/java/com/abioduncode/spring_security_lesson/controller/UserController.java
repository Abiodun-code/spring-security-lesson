package com.abioduncode.spring_security_lesson.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.dto.RegisterDto;
import com.abioduncode.spring_security_lesson.dto.ResendDto;
import com.abioduncode.spring_security_lesson.dto.VerifyUserDto;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth")
public class UserController {
  
  private final UserService userService;

  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> allUser() {
    List<User> users = userService.allUsers();
      return new ResponseEntity<>(users, HttpStatus.OK);
  }
  

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDto user){

    User registerUser = userService.register(user);
  
    return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
  }

  @PostMapping("/verify")
  public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {

    String verifyMsg = userService.verifyUser(verifyUserDto);
    
    return new ResponseEntity<>(verifyMsg, HttpStatus.OK);
  }
  
  @PostMapping("/resend-otp")
  public ResponseEntity<?> resendVerifyOtp(@RequestBody ResendDto resendDto ){

    User resendMsg = userService.resendVerifyCode(resendDto);

    return new ResponseEntity<>(resendMsg, HttpStatus.CREATED);
  }
  
}
