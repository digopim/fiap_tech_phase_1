package com.br.fiap.oficina.security;

import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.model.enums.Perfil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private static final long EXPIRATION_MILLIS = 1000L * 60 * 60; // 1h
    private final Key key;

    public JwtUtil(@Value("${jwt.key}") String secret) {
        byte[] keyBytes;
        try {
            keyBytes = java.util.Base64.getDecoder().decode(secret);
        } catch (IllegalArgumentException _) {
            keyBytes = secret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Credencial credencial) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(credencial.getLogin())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(EXPIRATION_MILLIS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .claim("perfil", credencial.getUsuario() != null && credencial.getUsuario().getPerfil() != null ? credencial.getUsuario().getPerfil().name() : Perfil.CLIENTE.name())
                .compact();
    }

    public String[] getSubject(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            String perfil = (String) claims.get("perfil");
            String subject = claims.getSubject();
            return new String[]{subject, perfil};
        } catch (JwtException _) {
            log.error("Token invalido");
            return new String[]{};
        }
    }

    public boolean validate(String token) {
        return getSubject(token) != null;
    }
}