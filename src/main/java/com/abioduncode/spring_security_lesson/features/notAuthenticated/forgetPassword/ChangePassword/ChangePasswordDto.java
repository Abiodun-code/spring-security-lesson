package com.abioduncode.spring_security_lesson.features.notAuthenticated.forgetPassword.changePassword;

import lombok.Data;

@Data
public class ChangePasswordDto {
  private String newPassword;
  private String confirmPassword;
}
