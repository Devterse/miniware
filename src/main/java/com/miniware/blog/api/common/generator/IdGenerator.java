package com.miniware.blog.api.common.generator;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class IdGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int DEFAULT_SIZE = 21;

    public String generateId() {
        return NanoIdUtils.randomNanoId(random, NanoIdUtils.DEFAULT_ALPHABET, DEFAULT_SIZE);
    }

}
