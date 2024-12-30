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

  public SignInService(JwtService jwtService, UserRepo userRepo) {
    this.jwtService = jwtService;
    this.userRepo = userRepo;
  }

  public Map<String, Object> signIn(SignInDto signInDto) {

    User user = userRepo.findByEmail(signInDto.getEmail())
      .orElseThrow(() -> new CustomException("Email not found"));

    if (!encoder.matches(signInDto.getPassword(), user.getPassword())) {
      throw new CustomException("Invalid password");
    }

    Authentication authentication = authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword()));

    if (authentication.isAuthenticated()) {
      String accessToken = jwtService.generateToken(signInDto.getEmail());
      String refreshToken = jwtService.generateRefreshToken(signInDto.getEmail());

      Map<String, Object> response = new HashMap<>();
      response.put("user", user);  // This will include the full user information
      response.put("accessToken", accessToken);
      response.put("refreshToken", refreshToken);

      return response; 
    } else {
      throw new CustomException("Authentication Failed");
    }
  }
}
