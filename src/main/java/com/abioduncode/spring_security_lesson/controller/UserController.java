package com.abioduncode.spring_security_lesson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  
  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public User register(@RequestBody User user){

    User registerUser = userService.register(user);

    return registerUser;
  }

  @PostMapping("/verify")
  public ResponseEntity<String> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            userService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("User verified successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
  }
  
  
}
