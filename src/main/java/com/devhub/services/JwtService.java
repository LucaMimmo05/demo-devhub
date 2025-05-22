package com.devhub.services;

import com.devhub.dto.UserResponse;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    private PrivateKey privateKey;

    public JwtService() {
        try {
            this.privateKey = loadPrivateKey();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }

    private PrivateKey loadPrivateKey() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/privateKey.pem")) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            byte[] decoded = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }
    }

    public String generateAccessToken(UserResponse userResponse) {
        return Jwt.issuer("secondomona-demo")
                .subject(String.valueOf(userResponse.getId()))
                .upn(userResponse.getEmail())
                .groups(Set.of(userResponse.getRole(), "access-token"))
                .claim("email", userResponse.getEmail())
                .claim("name", userResponse.getName())
                .claim("surname", userResponse.getSurname())
                .expiresIn(Duration.ofMinutes(15))
                .issuedAt(Instant.now())
                .sign(privateKey);
    }

    public String generateRefreshToken(UserResponse userResponse) {
        return Jwt.issuer("secondomona-demo")
                .subject(String.valueOf(userResponse.getId()))
                .upn(userResponse.getEmail())
                .groups(Set.of(userResponse.getRole(), "refresh-token"))
                .claim("email", userResponse.getEmail())
                .claim("name", userResponse.getName())
                .claim("surname", userResponse.getSurname())
                .expiresIn(Duration.ofHours(1))
                .issuedAt(Instant.now())
                .sign(privateKey);
    }
}
