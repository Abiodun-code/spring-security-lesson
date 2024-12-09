package com.abioduncode.spring_security_lesson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.dto.VerifyUserDto;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.services.UserService;

@RestController
public class UserController {
  
  private final UserService userService;

  public UserController(UserService userService){
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user){

    User registerUser = userService.register(user);
  
    return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
  }

  @PostMapping("/verify")
  public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
    try {
       String verifyMsg = userService.verifyUser(verifyUserDto);
      return new ResponseEntity<>(verifyMsg, HttpStatus.OK);
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
  }
  
  
}
