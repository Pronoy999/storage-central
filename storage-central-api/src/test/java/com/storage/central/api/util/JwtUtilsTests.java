package com.storage.central.api.util;

import com.storage.central.common.exceptions.InvalidTokenException;
import com.storage.central.common.model.dto.JwtData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class JwtUtilsTests {
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        jwtUtils = new JwtUtils("SECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEY");
    }

    @Test
    public void testGenerateJwt() {
        String token = jwtUtils.createJwt("dummyEmail", "dummyGuid");
        Assertions.assertNotNull(token);
        JwtData jwtData = jwtUtils.verifyJwt(token);
        Assertions.assertEquals("dummyEmail", jwtData.getEmailId());
        Assertions.assertEquals("dummyGuid", jwtData.getGuid());
    }

    @Test
    public void testInvalidJWT() {
        Assertions.assertThrows(InvalidTokenException.class, () -> jwtUtils.verifyJwt("INVALID_TOKEN"));
    }
}
