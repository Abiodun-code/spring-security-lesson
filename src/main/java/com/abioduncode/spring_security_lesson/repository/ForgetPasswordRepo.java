package com.abioduncode.spring_security_lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abioduncode.spring_security_lesson.models.ForgetPassword;
import com.abioduncode.spring_security_lesson.models.User;

public interface ForgetPasswordRepo extends JpaRepository<ForgetPassword, Integer> {
  ForgetPassword findByUser(User user);
}
