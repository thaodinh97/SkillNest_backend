package com.example.skillnest.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private static String JWT_SECRET;

    public String getUserIdFromToken(String token)
            throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        String userIdStr = signedJWT.getJWTClaimsSet().getStringClaim("userId");
        return userIdStr;
    }

}
