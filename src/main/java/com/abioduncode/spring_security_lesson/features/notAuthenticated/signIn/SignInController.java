package com.abioduncode.spring_security_lesson.features.notAuthenticated.signIn;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abioduncode.spring_security_lesson.features.notAuthenticated.signIn.refreshToken.RefreshTokenService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class SignInController {
  
  private final SignInService signInService;

  private final RefreshTokenService refreshTokenService;

  public SignInController(SignInService signInService, RefreshTokenService refreshTokenService){
    this.signInService = signInService;
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
    
    Map<String, Object> tokens = signInService.signIn(signInDto); // Returns accessToken and refreshToken
    
    return new ResponseEntity<>(tokens, HttpStatus.OK);
  }

  @GetMapping("/refresh-token/{refreshToken}")
  public ResponseEntity<Map<String, String>> refreshToken(@PathVariable String refreshToken) {
    
    Map<String, String> tokens = refreshTokenService.refreshAccessToken(refreshToken);
    
    return ResponseEntity.ok(tokens);
  }
  
}
