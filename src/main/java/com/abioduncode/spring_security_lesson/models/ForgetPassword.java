package com.abioduncode.spring_security_lesson.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
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

  private boolean emailVerified = false;

  private LocalDateTime otpExpiry;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @JsonBackReference
  private User user;

  public ForgetPassword() {}

  @Override
  public String toString() {
    return "ForgetPassword{" +
        "fpid=" + fpid +
        ", otp=" + otp +
        ", emailVerified=" + emailVerified +
        ", otpExpiry=" + otpExpiry +
        ", user=" + user +
        '}';
  }
}
