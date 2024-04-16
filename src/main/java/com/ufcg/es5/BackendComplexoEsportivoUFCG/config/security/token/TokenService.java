package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final String UNAUTHORIZED = "";

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private SaceUserService userService;

    public String generateToken(SaceUser user, Long expirationMinutes) {
        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate(expirationMinutes))
                    .sign(getAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token.", exception);
        }
    }

    public String validateToken(String token) {
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

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Instant generateExpirationDate(Long expirationMinutes) {
        return LocalDateTime.now().plusMinutes(expirationMinutes).toInstant(ZoneOffset.of("-03:00"));
    }

    @Transactional
    public Authentication getAuthentication(String username) {
        SaceUser user = userService.findByEmail(username).orElseGet(
                () -> userService.findByStudentId(username).orElseThrow(
                        () -> new SaceResourceNotFoundException(
                                SaceUserExceptionMessages.USER_WITH_USERNAME_NOT_FOUND.formatted(username)
                        )
                )
        );

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
