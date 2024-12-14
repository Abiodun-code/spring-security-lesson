package com.abioduncode.spring_security_lesson.features.notAuthenticated.signIn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.features.jwt.JwtService;

@Service
public class SignInService {

  private final JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  public SignInService( JwtService jwtService) {
    this.jwtService = jwtService;
  }

  public Map<String, String> signIn(SignInDto signInDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword()));

    if (authentication.isAuthenticated()) {
      String accessToken = jwtService.generateToken(signInDto.getEmail());
      String refreshToken = jwtService.generateRefreshToken(signInDto.getEmail());

      Map<String, String> tokens = new HashMap<>();
      tokens.put("accessToken", accessToken);
      tokens.put("refreshToken", refreshToken);

      return tokens;
    }else{
      throw new CustomException("Authentication Failed");
    }
  }
}

