package com.abioduncode.spring_security_lesson.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment")
public class Payment {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer pyId;

  private Double amount;

  private String type; //Deposit or withdrawal

  private Date timeStamp;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  @JsonBackReference
  private User user;

  public Payment(){}

  @Override
  public String toString(){
    return "Payment{" +
        "pyId=" + pyId +
        ", amount=" + amount +
        ", type=" + type +
        ", timeStamp=" + timeStamp +
        ", user=" + user +
        '}';
  }
}
