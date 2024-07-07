package com.storage.central.service;

import com.storage.central.common.model.requests.CreateUserRequest;
import com.storage.central.common.model.responses.CreateUserResponse;
import com.storage.central.common.repository.CredentialRepository;
import com.storage.central.common.repository.UserRepository;
import com.storage.central.test.TestDataGenerator;
import com.storage.central.utils.JwtHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;

public class RegisterUserServiceTests {
    private RegisterUserService registerUserService;
    private UserRepository userRepository;
    private CredentialRepository credentialRepository;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        userRepository = mock(UserRepository.class);
        credentialRepository = mock(CredentialRepository.class);
        Field keyField = JwtHelper.class.getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(null, "SECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEYSECRET_KEY");
        registerUserService = new RegisterUserService(userRepository, credentialRepository);
    }

    @Test
    public void testRegisterUser() {
        CreateUserRequest request = TestDataGenerator.generateCreateUserRequest();
        ResponseEntity<CreateUserResponse> actualResponse = registerUserService.registerUser(request);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertNotNull(actualResponse.getBody());
        Assertions.assertNotNull(actualResponse.getBody().getGuid());
        Assertions.assertNotNull(actualResponse.getBody().getJwtToken());
    }
}
