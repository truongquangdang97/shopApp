package com.project.shopapp.components;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.project.shopapp.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

      @Value("${jwt.expiration}")
      private int expiration; // lưu vào biến môi trường

      @Value("${jwt.secretKey}")
      private String secretKey;

      public String generateToken(User user) throws Exception{
             Map<String,Object> claims = new HashMap<>();
            // this.generateSecretKey();
            claims.put("phoneNumber", user.getPhoneNumber());
            try{
                  String token = Jwts.builder()
                                    .setClaims(claims)
                                    .setSubject(user.getPhoneNumber())
                                    .setExpiration(new Date(System.currentTimeMillis()+expiration*1000))
                                    .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                                    .compact();
                  return token;
            }catch(Exception e){
                  throw new InvalidAlgorithmParameterException("cannot create jwt token");
                  // return null;
            }
      }

      private Key getSignInKey(){
            byte[] bytes = Decoders.BASE64.decode(secretKey); //     Keys.hmacShaKeyFor(Decoders.BASE64.decode("zJzr2NZvFW8oCjWbyQ35lg/6mePWTZlB4ZcRjr2NE8I="))
            return Keys.hmacShaKeyFor(bytes);
      }

      private String generateSecretKey(){
            SecureRandom random = new SecureRandom();
            byte[] keyBytes = new byte[32];
            random.nextBytes(keyBytes);
            String secretKey = Encoders.BASE64.encode(keyBytes);
            return secretKey;
      }

      private Claims extractAllClaims(String token){
            return Jwts.parserBuilder()
                        .setSigningKey(getSignInKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
      }
      public <T> T extractClaim(String token, Function<Claims, T> claimsResolver ){
            final Claims claims =  this.extractAllClaims(token);
            return claimsResolver.apply(claims);

      }

      private boolean isTokenExpired(String token){
            Date expirationDate = this.extractClaim(token, Claims::getExpiration);

            return expirationDate.before(new Date());
      }
      public String extractPhoneNumver(String token){
            return extractClaim(token, Claims::getSubject);
      }

      public boolean validateToken(String token, UserDetails userDetails){
            String phoneNumber = extractPhoneNumver(token);
            return (phoneNumber.equals(userDetails.getUsername()))
                  && !isTokenExpired(token);
      }
}
