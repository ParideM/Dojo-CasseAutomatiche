package it.beije.dojo.CasseAutomatiche.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;
    

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractClaims(token);
        String role = claims.get("role", String.class);
        return List.of(new SimpleGrantedAuthority(role));
    }

    private Claims extractClaims(String token) {
    	SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes()); 
        return Jwts.parser()
        	    .verifyWith(key)
        	    .build()
        	    .parseSignedClaims(token)
        	    .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            // Handle the exception as needed
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String generateToken(String username, String role) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expiration);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes()); 

        return Jwts.builder()
                .subject(username)
                .claim("role", role)  
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }
}
