package com.abioduncode.spring_security_lesson.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "forget_password")
public class ForgetPassword {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer fpid;

  private Integer otp;

  private LocalDateTime otpExpiry;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public ForgetPassword() {}

  @Override
  public String toString() {
    return "ForgetPassword{" +
        "fpid=" + fpid +
        ", otp=" + otp +
        ", otpExpiry=" + otpExpiry +
        ", user=" + user +
        '}';
  }
}
