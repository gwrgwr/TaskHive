package com.example.taskhive.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskhive.domain.user.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${taskhive.jwt.secret}")
    private String secret;

    private Algorithm algorithm;

    private Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(secret);
        }
        return algorithm;
    }

    public String generateToken(Users user) {
        return JWT.create()
                .withSubject(user.getUser())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .sign(getAlgorithm());
    }

    // Valida o token JWT e retorna se é válido
    public boolean validateToken(String token, String username) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getSubject().equals(username) && !isTokenExpired(decodedJWT);
        } catch (JWTVerificationException e) {
            return false; // Token inválido
        }
    }

    public String extractUsername(String token) {
        return verifyToken(token).getSubject();
    }

    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    private boolean isTokenExpired(DecodedJWT token) {
        return token.getExpiresAt().before(new Date());
    }
}
