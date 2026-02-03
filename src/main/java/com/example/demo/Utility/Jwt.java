package com.example.demo.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {
    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 28*24*60*60*1000;
    private Jwt(){}
    public static String generateToken(int userId,String email){
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email",email)
                .claim("role","USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY)
                .compact();
        }
        public static boolean validateToken(String token){
            try{
                Jwts.parserBuilder()
                        .setSigningKey(KEY)
                        .build()
                        .parseClaimsJws(token);
                return true;
            }catch(Exception e){
                return false;
            }
        }
}
