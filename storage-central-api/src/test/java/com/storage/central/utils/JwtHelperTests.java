package com.storage.central.utils;

import com.storage.central.common.exceptions.InvalidTokenException;
import com.storage.central.common.model.dto.JwtData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class JwtHelperTests {

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field keyField = JwtHelper.class.getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(null, "SECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEY");
    }


    @Test
    public void testGenerateToken() {
        String token = JwtHelper.createJwt("dummyemail", "dummyguid");
        Assertions.assertNotNull(token);
        JwtData jwtResponse = JwtHelper.verifyJwt(token);
        Assertions.assertEquals("dummyemail", jwtResponse.getEmail());
        Assertions.assertEquals("dummyguid", jwtResponse.getGuid());
    }

    @Test
    public void testInvalidTokenGeneration() {
        Assertions.assertThrows(InvalidTokenException.class, () -> JwtHelper.verifyJwt("Invalid Token"));
    }
}
