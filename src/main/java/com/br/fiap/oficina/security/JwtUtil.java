package com.br.fiap.oficina.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long expirationMillis = 1000L * 60 * 60; // 1h

    public JwtUtil() {
        // troque esta string por uma secret segura em production (mínimo 256 bits)
        String secret = "troque-esta-secret-por-uma-mais-segura-e-256-bit-minimo!troque-esta-secret-por-uma-mais-segura";
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String subject) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException ex) {
            return null;
        }
    }

    public boolean validate(String token) {
        return getSubject(token) != null;
    }
}