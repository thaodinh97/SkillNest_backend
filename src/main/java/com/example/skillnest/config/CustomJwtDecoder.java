package com.example.skillnest.config;

import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.skillnest.dto.requests.IntrospectRequest;
import com.example.skillnest.services.AuthService;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Autowired
    private AuthService authService;

    private NimbusJwtDecoder jwtDecoder;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = authService.introspectToken(
                    IntrospectRequest.builder().token(token).build());

            if (!response.isValid()) {
                throw new JwtException("Invalid token");
            }
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(jwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(JWT_SECRET.getBytes(), "HS512");
            jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return jwtDecoder.decode(token);
    }
}
