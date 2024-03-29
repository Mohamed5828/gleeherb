package com.mohamed.egHerb.service;

import com.mohamed.egHerb.entity.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET_KEY = "RpR2qwuAcwTp4VcCwElFXH37YNsVdO6IoWC0lqJGGwZ1VqG2y8muv2OWoVwy2heN";
    private Logger log;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        try {
            Map<String , Object> claims = new HashMap<>();
            claims.put("userId" ,((AppUser) userDetails).getId());
            return generateToken(claims, userDetails);
        } catch (Exception e) {
            throw new RuntimeException("Error generating token: " + e.getMessage(), e);
        }
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Include user roles in the claims
        extraClaims.put("roles", roles);
        try {
            long expirationMillis = 1000 * 60 * 60; // One hour (adjust as needed)
            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error generating token: " + e.getMessage(), e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired token: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error validating token: " + e.getMessage(), e);
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            throw new RuntimeException("Error checking token expiration: " + e.getMessage(), e);
        }
    }

    private Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting expiration from token: " + e.getMessage(), e);
        }
    }

    public Integer extractUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AppUser) {
            return ((AppUser) authentication.getPrincipal()).getId();
        } else {
            log.error("Unable to extract user ID from token.");
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            System.out.println("Attempting to parse token: " + token);
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            System.out.println("Claims extracted successfully: " + claims);

            return claims;
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Token has expired", e);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            System.out.println("Error extracting claims from token: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error extracting claims from token", e);
        }
    }

    private Key getSignInKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
            System.out.println("Decoded key: " + new String(keyBytes)); // Print decoded key for debugging
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            System.out.println("Error getting signing key: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error getting signing key", e);
        }
    }


}
