package com.storage.central.api.util;

import com.storage.central.common.exceptions.InvalidTokenException;
import com.storage.central.common.model.dto.JwtData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class JwtUtilsTests {

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field field = JwtUtils.class.getDeclaredField("key");
        field.setAccessible(true);
        field.set(null, "SECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEY");
    }

    @Test
    public void testGenerateJwt() {
        String token = JwtUtils.createJwt("dummyEmail", "dummyGuid");
        Assertions.assertNotNull(token);
        JwtData jwtData = JwtUtils.verifyJwt(token);
        Assertions.assertEquals("dummyEmail", jwtData.getEmailId());
        Assertions.assertEquals("dummyGuid", jwtData.getGuid());
    }

    @Test
    public void testInvalidJWT() {
        Assertions.assertThrows(InvalidTokenException.class, () -> JwtUtils.verifyJwt("INVALID_TOKEN"));
    }
}
