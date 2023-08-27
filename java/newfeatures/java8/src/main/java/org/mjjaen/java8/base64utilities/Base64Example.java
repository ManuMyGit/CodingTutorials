package org.mjjaen.java8.base64utilities;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java8.Example;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class Base64Example implements Example {
    @Override
    public void runExample() {
        log.info("Base 64 example ...");
        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String encodedStringWithoutPadding = Base64.getEncoder().withoutPadding().encodeToString(originalInput.getBytes());
        log.info("Original string: " + originalInput);
        log.info("Encoded string: " + encodedString);
        log.info("Encoded string without padding: " + encodedStringWithoutPadding);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        log.info("Decoded string: " + decodedString);

        log.info("String to byte array");
        byte[] result = originalInput.getBytes();
        log.info("String to byte array with encoding");
        result = originalInput.getBytes(StandardCharsets.UTF_8);
    }
}
