package com.devhub.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ApplicationScoped
public class KeyProducer {

    @Produces
    @ApplicationScoped
    public PublicKey producePublicKey() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/publicKey.pem");
        if (is == null) {
            throw new IllegalStateException("Public key file not found!");
        }

        String publicKeyPem = new String(is.readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(publicKeyPem);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(keySpec);
    }
}
