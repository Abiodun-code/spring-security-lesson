package com.abioduncode.spring_security_lesson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
  
}
