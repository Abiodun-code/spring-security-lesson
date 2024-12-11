package com.abioduncode.spring_security_lesson.controller;

import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.dto.ForgetEmailDto;
import com.abioduncode.spring_security_lesson.services.ForgetPasswordService;

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
  public ResponseEntity<?> generateOtp(@RequestBody ForgetEmailDto forgetEmailDto) {

    String forgetMsg = forgetPasswordService.generateOtp(forgetEmailDto);
    return new ResponseEntity<>(forgetMsg, HttpStatus.OK);
  }
  
}
