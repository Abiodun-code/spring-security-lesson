package com.abioduncode.spring_security_lesson.dto;

import lombok.Data;

@Data
public class LoginDto {

  private String email;

  private String password;

  public String getUsername(){
    return email;
  }

  public void setUsername(String email){
    this.email = email;
  }
}
