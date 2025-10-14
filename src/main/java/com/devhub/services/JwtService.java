package com.devhub.services;

import com.devhub.dto.LoginResponse;
import com.devhub.dto.UserResponse;
import com.devhub.models.role.Role;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@ApplicationScoped
public class JwtService {



    public String generateAccessToken(UserResponse user) {
        return Jwt.issuer("demo-devhub")
                .subject(String.valueOf(user.getId()))
                .upn(user.getEmail())
                .groups(Set.of(user.getRole().name(), "access-token"))
                .claim("nickname", user.getEmail())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .expiresIn(Duration.ofMinutes(15))
                .issuedAt(Instant.now())
                .sign();
    }

    public String generateRefreshToken(UserResponse user) {
        return Jwt.issuer("demo-devhub")
                .subject(String.valueOf(user.getId()))
                .upn(user.getEmail())
                .groups(Set.of(user.getRole().name(), "refresh-token"))
                .claim("nickname", user.getEmail())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .expiresIn(Duration.ofDays(7))
                .issuedAt(Instant.now())
                .sign();
    }


    public LoginResponse verifyToken(String jwtString, String requiredGroup) {
        if (jwtString == null || jwtString.isEmpty()) {
            throw new WebApplicationException("Token is required", 400);
        }

        try {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setSkipAllValidators()
                    .setDisableRequireSignature()
                    .setSkipSignatureVerification()
                    .build();

            JwtClaims claims = jwtConsumer.processToClaims(jwtString);

            List<String> groupsList = claims.getStringListClaimValue("groups");
            Set<String> groups = new HashSet<>(groupsList != null ? groupsList : List.of());
            if (!groups.contains(requiredGroup)) {
                throw new WebApplicationException("Token missing required group", 401);
            }

            Long exp = claims.getExpirationTime().getValue();
            if (exp != null && Instant.ofEpochSecond(exp).isBefore(Instant.now())) {
                throw new WebApplicationException("Token expired", 401);
            }

            UserResponse user = new UserResponse(
                    Long.valueOf(claims.getSubject()),
                    (String) claims.getClaimValue("email"),
                    (String) claims.getClaimValue("name"),
                    (String) claims.getClaimValue("surname"),
                    Role.valueOf(
                            groups.stream()
                                    .filter(g -> !g.equals(requiredGroup))
                                    .findFirst()
                                    .orElse("USER"))
            );

            return new LoginResponse("Token is valid", null, null, user);

        } catch (InvalidJwtException e) {
            throw new WebApplicationException("Invalid token: " + e.getMessage(), 401);
        } catch (Exception e) {
            throw new WebApplicationException("Token processing error: " + e.getMessage(), 401);
        }
    }
}
