package com.abioduncode.spring_security_lesson.features.notAuthenticated.signIn;

import lombok.Data;

@Data
public class SignInDto {
  
  private String email;

  private String password;
}
