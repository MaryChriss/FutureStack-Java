package br.com.fiap.sprint3.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String secret = "chaveSuperSecreta123"; // ideal buscar do application.properties
    private final long expirationMs = 3600000; // 1h

    public String generateFromAuth(Authentication authentication) {
        String username = authentication.getName();

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
                .sign(Algorithm.HMAC256(secret));
    }
}
