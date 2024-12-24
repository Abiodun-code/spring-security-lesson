package com.abioduncode.spring_security_lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abioduncode.spring_security_lesson.models.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
  
}
