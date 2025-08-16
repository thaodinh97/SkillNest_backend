package com.example.skillnest.utils;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private static String JWT_SECRET;

    public String getUserIdFromToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        String userIdStr = signedJWT.getJWTClaimsSet().getStringClaim("userId");
        return userIdStr;
    }
}
