package com.abioduncode.spring_security_lesson.features.notAuthenticate.forgetPassword.ChangePassword;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ChangePasswordController {

  private final ChangePasswordService changePasswordService;

  public ChangePasswordController(ChangePasswordService changePasswordService){
    this.changePasswordService = changePasswordService;
  }
  
  @PostMapping("/forget-change/{email}")
  public ResponseEntity<?> ChangePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable String email){
    
    String changeMsg = changePasswordService.changePassword(changePasswordDto, email);

    return new ResponseEntity<>(changeMsg, HttpStatus.OK);
  }
}
