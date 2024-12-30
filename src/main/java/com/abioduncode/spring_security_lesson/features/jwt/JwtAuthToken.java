package com.abioduncode.spring_security_lesson.features.jwt;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abioduncode.spring_security_lesson.models.UserPrincipal;

public class JwtAuthToken implements Authentication {

  private boolean isAuthenticated;
  private final UserPrincipal userPrincipal;

  public JwtAuthToken(boolean isAuthenticated, UserPrincipal userPrincipal) {
    this.isAuthenticated = isAuthenticated;
    this.userPrincipal = userPrincipal;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return userPrincipal.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    return userPrincipal.getAuthorities();
  }

  @Override
  public Object getCredentials() {
    // TODO Auto-generated method stub
    return null; //Credientials are not needed for JWT
  }

  @Override
  public Object getDetails() {
    // TODO Auto-generated method stub
    return null; // Can include additional details if needed
  }

  @Override
  public Object getPrincipal() {
    // TODO Auto-generated method stub
    return userPrincipal;
  }

  @Override
  public boolean isAuthenticated() {
    // TODO Auto-generated method stub
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    this.isAuthenticated = isAuthenticated;
  }
  
}
