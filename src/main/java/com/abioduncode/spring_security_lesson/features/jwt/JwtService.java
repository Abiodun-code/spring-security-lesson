package com.abioduncode.spring_security_lesson.features.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secretKey;


  // public JwtService(){
  //   try{
  //     KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
  //     SecretKey SK = keyGen.generateKey();
  //     secretKey = Base64.getEncoder().encodeToString(SK.getEncoded());
  //   }catch (NoSuchAlgorithmException e){
  //     throw new CustomException("" + e);
  //   }
  // }

  public String generateToken(String email) {
    Map<String, Object> claims = new HashMap<>();

    return Jwts.builder()
      .claims()
      .add(claims)
      .subject(email)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
      .and()
      .signWith(getKey())
      .compact();
  }

   public String generateRefreshToken(String email) {
    // TODO Auto-generated method stub
    return Jwts.builder()
      .claims(new HashMap<>())
      .subject(email)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
      .signWith(getKey())
      .compact();
  }

  private SecretKey getKey() {

    byte[] keyBytes =Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractEmail(String token) {
    // extract username from jwt token
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token){
    return Jwts.parser()
      .verifyWith(getKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String userEmail = extractEmail(token);
    return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
  
  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

}