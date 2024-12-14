package com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.ChangePassword;

import lombok.Data;

@Data
public class ChangePasswordDto {
  private String newPassword;
  private String confirmPassword;
}
