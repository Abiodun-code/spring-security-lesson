package com.abioduncode.spring_security_lesson.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.models.UserPrincipal;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class UserPrincipalService implements UserDetailsService {

  private UserRepo userRepo;

  public UserPrincipalService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new UsernameNotFoundException("User not found with email" + email));

    System.out.println("User found: " + user);

    return new UserPrincipal(user);
  }
  
}
