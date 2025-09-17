package com.devhub.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class KeyReader {
    public static void main(String[] args) {
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("META-INF/publicKey.pem");

        if (is == null) {
            System.out.println("File not found!");
            return;
        }

        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            StringBuilder key = new StringBuilder();
            while (scanner.hasNextLine()) {
                key.append(scanner.nextLine()).append("\n");
            }
            System.out.println("Private key content:\n" + key);
        }
    }
}
