package com.abioduncode.spring_security_lesson.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    // TODO Auto-generated method stub
    Optional<User> user = userRepo.findByEmail(email);

    if (user == null) {
      System.out.println("User not found with email: " + email);
      throw new UsernameNotFoundException("User not found with email: " + email);
    }

    System.out.println("User found: " + user.get());

    return new UserPrincipal(user.get());
  }
  
}
