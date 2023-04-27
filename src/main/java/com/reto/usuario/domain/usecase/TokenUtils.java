package com.reto.usuario.domain.usecase;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TokenUtils {

    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;
    private static final String ACCESS_TOKEN_SECRET = "8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@";

    private TokenUtils() {
    }

    public static String createToken(String email, List<String> rol, String name, String lastName) {
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);
        extra.put("lastName", lastName);
        extra.put("rol", rol);
        byte[] keyBytes = ACCESS_TOKEN_SECRET.getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(key)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) throws JwtException {
        Jws<Claims> jsonWebSignature = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                .build()
                .parseClaimsJws(token);
        Claims claims = jsonWebSignature.getBody();
        String email = claims.getSubject();
        List<String> rol =  claims.get("rol", ArrayList.class);
        return new UsernamePasswordAuthenticationToken(email, null,
                rol.stream().map( SimpleGrantedAuthority::new )
                .toList());
    }
}
