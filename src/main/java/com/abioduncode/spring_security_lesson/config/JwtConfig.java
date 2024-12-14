package com.abioduncode.spring_security_lesson.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abioduncode.spring_security_lesson.features.jwt.JwtService;
import com.abioduncode.spring_security_lesson.features.notAuthenticated.UserPrincipalService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
class JwtConfig extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private ApplicationContext context;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String email = null;

    if(authHeader != null && authHeader.startsWith("Bearer ")){
      token = authHeader.substring(7);
      email = jwtService.extractEmail(token);
    }

    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

      UserDetails userDetails = context.getBean(UserPrincipalService.class).loadUserByUsername(email);

      if(jwtService.validateToken(token, userDetails)){
          UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          userPassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(userPassToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}