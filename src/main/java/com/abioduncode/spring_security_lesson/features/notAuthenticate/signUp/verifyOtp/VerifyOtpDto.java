package com.abioduncode.spring_security_lesson.features.notAuthenticate.signUp.verifyOtp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDto {
  String email;
  Integer otp;
}
