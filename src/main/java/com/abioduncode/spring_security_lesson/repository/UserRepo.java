package com.abioduncode.spring_security_lesson.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abioduncode.spring_security_lesson.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

  User findByEmail(String email);
}
