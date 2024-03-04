package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final String UNAUTHORIZED = "";

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(getAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token.", exception);
        }
    }

    public String validateToken(String token){
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException exception) {
            return UNAUTHORIZED;
        }
    }

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC256(secret);
    }
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusMinutes(30L).toInstant(ZoneOffset.of("-03:00"));
    }
}
