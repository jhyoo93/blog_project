package com.dev.back_board.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
  
  private String secrtKey = "S3cr3tK3y";

  public String create(String email){

      Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
      Key key = Keys.hmacShaKeyFor(secrtKey.getBytes(StandardCharsets.UTF_8));

      String jwt = Jwts.builder()
        .signWith(key, SignatureAlgorithm.ES256)
        .setSubject(email).setIssuedAt(new Date()).setExpiration(expiredDate)
        .compact();

      return jwt;   

  }

  public String validate(String jwt){
      Claims claims = null;
      Key key = Keys.hmacShaKeyFor(secrtKey.getBytes(StandardCharsets.UTF_8));

      try{
          claims = Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(jwt)
              .getBody();
      } catch (Exception e){
          e.printStackTrace();
          return null;
      }

      return claims.getSubject();
  }

}