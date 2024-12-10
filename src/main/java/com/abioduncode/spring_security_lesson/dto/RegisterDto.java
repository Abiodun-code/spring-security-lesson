package com.abioduncode.spring_security_lesson.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegisterDto {

  private int id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

}
