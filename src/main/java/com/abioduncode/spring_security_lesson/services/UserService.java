package com.abioduncode.spring_security_lesson.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class UserService {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  @Autowired
  private UserRepo userRepo;
 
  public User register(User user){
    user.setPassword(encoder.encode(user.getPassword()));
    return userRepo.save(user);
  }
}
