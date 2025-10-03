package com.example.skillnest.services;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.skillnest.dto.requests.AuthRequest;
import com.example.skillnest.dto.requests.IntrospectRequest;
import com.example.skillnest.dto.requests.LogoutRequest;
import com.example.skillnest.dto.requests.RefreshRequest;
import com.example.skillnest.dto.responses.AuthResponse;
import com.example.skillnest.dto.responses.IntrospectResponse;
import com.example.skillnest.entity.InvalidatedToken;
import com.example.skillnest.entity.User;
import com.example.skillnest.exception.AppException;
import com.example.skillnest.exception.ErrorCode;
import com.example.skillnest.repositories.InvalidatedTokenRepository;
import com.example.skillnest.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    @NonFinal
    @Value("${jwt.secret}")
    protected String JWT_SECRET;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long JWT_VALIDATION_DURATION;

    @NonFinal
    @Value("${jwt.refreshabl-duration}")
    protected Long JWT_REFRESHABLE_DURATION;

    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @Transactional
    public AuthResponse authenticate(AuthRequest authRequest) {
        var user = userRepository
                .findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED_EXCEPTION);
        }
        var token = generateToken(user);
        log.info("Token generated: {}", token);
        return AuthResponse.builder().token(token).isAuthenticated(true).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(JWT_SECRET.getBytes());
        SignedJWT jwt = SignedJWT.parse(token);
        Date expiryTime = (isRefresh)
                ? new Date(jwt.getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(JWT_REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : jwt.getJWTClaimsSet().getExpirationTime();
        var verified = jwt.verify(verifier);
        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED_EXCEPTION);
        }

        if (invalidatedTokenRepository.existsById(jwt.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHORIZED_EXCEPTION);
        }
        return jwt;
    }

    public void logout(LogoutRequest logoutRequest) throws JOSEException, ParseException {
        try {
            var signToken = verifyToken(logoutRequest.getToken(), true);
            String jti = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jti).expiration(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException appException) {
            log.info("Token already expired: {}", appException.getMessage());
        }
    }

    public AuthResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException {
        var signToken = verifyToken(request.getToken(), true);
        String jti = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jti).expiration(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);
        var userId = signToken.getJWTClaimsSet().getStringClaim("userId");
        var user = userRepository
                .findById(UUID.fromString(userId))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var token = generateToken(user);
        return AuthResponse.builder().token(token).isAuthenticated(true).build();
    }

    public IntrospectResponse introspectToken(IntrospectRequest introspectRequest)
            throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issuer("skill-nest")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(JWT_VALIDATION_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("userId", user.getId().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(JWT_SECRET.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }
}
