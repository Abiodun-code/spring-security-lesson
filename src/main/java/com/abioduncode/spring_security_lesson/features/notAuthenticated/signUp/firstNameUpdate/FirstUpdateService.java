package com.abioduncode.spring_security_lesson.features.notAuthenticated.signUp.firstNameUpdate;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class FirstUpdateService {
  
  private final UserRepo userRepo;

  public FirstUpdateService(UserRepo userRepo){
    this.userRepo = userRepo;
  }

  public User firstNameUpdate(FirstUpdateDto firstUpdateDto, String email){

    User user = userRepo.findByEmail(email)
    .orElseThrow(() -> new CustomException("Email not found"));

    if(user != null){
      user.setFirstName(firstUpdateDto.getFirstName());
      return userRepo.save(user);
    }
    
    throw new CustomException("User not found.");
  }
}
