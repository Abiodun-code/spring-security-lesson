package com.abioduncode.spring_security_lesson.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String firstName;

  private String lastName;

  @Column(unique = true)
  private String email;

  private String password;

  private boolean emailVerified = false;

  private Integer otp;

  private LocalDateTime otpExpiry;

  private Double balance;

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "fp_id", referencedColumnName = "fpid")
  @JsonManagedReference
  private ForgetPassword forgetPassword;

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "im_id", referencedColumnName = "imId")
  @JsonManagedReference
  private Image image;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Payment> payment;

  // No args constructor
  public User() {

  }

  // All args constructor
  public User(int id, String firstName, String lastName, String email, String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  // Getter and Setter
  public String getUsername() {
    return email;
  }

  public void setUsername(String email) {
    this.email = email;
  }

  // toString
  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", emailVerified=" + emailVerified +
        ", otp='" + otp + '\'' +
        ", otpExpiry=" + otpExpiry +
        ", balance=" + balance +
        '}';
  }
}