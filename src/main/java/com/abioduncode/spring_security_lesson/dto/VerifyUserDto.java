package com.abioduncode.spring_security_lesson.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {
  String email;
  String otp;
}
