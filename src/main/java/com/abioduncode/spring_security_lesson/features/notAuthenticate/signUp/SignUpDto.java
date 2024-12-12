package com.abioduncode.spring_security_lesson.features.notAuthenticate.signUp;

import lombok.Data;

@Data
public class SignUpDto {

  private int id;

  private String firstName;

  private String lastName;

  private String email;

  private String password; 
}
