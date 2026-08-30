package com.br.fiap.oficina.security;

import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.model.enums.Perfil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRATION_MILLIS = 1000L * 60 * 60; // 1h
    private static final String SECRET = "ZmlhcHBvc3RlY2hhcnF1aXRldHVyYXNvZnR3YXJlZmFzZTE=";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

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
            return null;
        }
    }

    public boolean validate(String token) {
        return getSubject(token) != null;
    }
}