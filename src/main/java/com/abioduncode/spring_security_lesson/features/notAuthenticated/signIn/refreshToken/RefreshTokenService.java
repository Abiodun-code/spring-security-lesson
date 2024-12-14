package com.abioduncode.spring_security_lesson.features.notAuthenticated.signIn.refreshToken;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.abioduncode.spring_security_lesson.exceptions.CustomException;
import com.abioduncode.spring_security_lesson.features.jwt.JwtService;

@Service
public class RefreshTokenService {

  private final JwtService jwtService;

  public RefreshTokenService(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  public Map<String, String> refreshAccessToken(String refreshToken) {
    String email = jwtService.extractEmail(refreshToken);

    if (email != null && !jwtService.isTokenExpired(refreshToken)) {
      String newAccessToken = jwtService.generateToken(email);

      Map<String, String> tokens = new HashMap<>();
      tokens.put("accessToken", newAccessToken);
      tokens.put("refreshToken", refreshToken); // Reuse or issue a new refresh token here

      return tokens;
    }

    throw new CustomException("Invalid or expired refresh token");
  }
}
