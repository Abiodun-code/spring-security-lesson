package com.abioduncode.spring_security_lesson.features.notAuthenticated.signIn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.features.jwt.JwtService;
import com.abioduncode.spring_security_lesson.models.User;
import com.abioduncode.spring_security_lesson.repository.UserRepo;

@Service
public class SignInService {

  private final JwtService jwtService;

  private final UserRepo userRepo;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  @Autowired
  private AuthenticationManager authenticationManager;

  public SignInService( JwtService jwtService, UserRepo userRepo) {
    this.jwtService = jwtService;
    this.userRepo = userRepo;
  }

  public Map<String, String> signIn(SignInDto signInDto) {

    User users = userRepo.findByEmail(signInDto.getEmail())
    .orElseThrow(()-> new CustomException("Email not found"));

    if (!encoder.matches(signInDto.getPassword(), users.getPassword())) {
      throw new  CustomException("Invalid password");
    }
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword()));

    if (authentication.isAuthenticated()) {
      String accessToken = jwtService.generateToken(signInDto.getEmail());
      String refreshToken = jwtService.generateRefreshToken(signInDto.getEmail());

      Map<String, String> tokens = new HashMap<>();
      tokens.put("email", users.getEmail());
      tokens.put("password", users.getPassword());
      tokens.put("accessToken", accessToken);
      tokens.put("refreshToken", refreshToken);

      return tokens;
    }else{
      throw new CustomException("Authentication Failed");
    }
  }
}

