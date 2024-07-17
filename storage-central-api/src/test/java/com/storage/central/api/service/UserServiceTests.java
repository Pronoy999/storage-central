package com.storage.central.api.service;

import com.storage.central.api.repository.CredentialRepository;
import com.storage.central.api.repository.UserRepository;
import com.storage.central.api.test.TestDataGenerator;
import com.storage.central.api.util.JwtUtils;
import com.storage.central.common.model.requests.CreateUserRequest;
import com.storage.central.common.model.responses.CreateUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

public class UserServiceTests {
    UserService userService;
    UserRepository userRepository;
    CredentialRepository credentialRepository;
    JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        credentialRepository = mock(CredentialRepository.class);
        jwtUtils = mock(JwtUtils.class);
        userService = new UserService(userRepository, credentialRepository, jwtUtils);
    }

    @Test
    public void testSuccessfulUserRegister() {
        when(jwtUtils.createJwt(anyString(), anyString())).thenReturn("sample JWT");
        when(userRepository.save(any())).thenReturn(null);
        when(credentialRepository.save(any())).thenReturn(null);
        CreateUserRequest request = TestDataGenerator.generateCreateUserRequest();

        ResponseEntity<?> actualResponse = userService.registerUser(request);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertNotNull(((CreateUserResponse) actualResponse.getBody()).getGuid());
        Assertions.assertNotNull(((CreateUserResponse) actualResponse.getBody()).getJwtToken());
        verify(userRepository, times(1)).save(any());
        verify(credentialRepository, times(1)).findByEmailId(anyString());
        verify(credentialRepository, times(1)).save(any());
        verify(jwtUtils, times(1)).createJwt(anyString(), anyString());
        verifyNoMoreInteractions(userRepository, credentialRepository, jwtUtils);
    }

    @Test
    public void testSuccessfulUserLogin() {
        when(jwtUtils.createJwt(anyString(), anyString())).thenReturn("sample JWT");
        when(credentialRepository.findByEmailIdAndPasswordAndIsActive(any(), anyString(), anyBoolean())).thenReturn(TestDataGenerator.generateCredentialList());

        ResponseEntity<?> actualResponse = userService.loginUsers(TestDataGenerator.generateLoginUserRequest());

        Assertions.assertNotNull(actualResponse);
        Assertions.assertNotNull(((CreateUserResponse) actualResponse.getBody()).getGuid());
        Assertions.assertNotNull(((CreateUserResponse) actualResponse.getBody()).getJwtToken());
        verify(credentialRepository, times(1)).findByEmailIdAndPasswordAndIsActive(any(), anyString(), anyBoolean());
        verify(jwtUtils, times(1)).createJwt(anyString(), anyString());
        verifyNoMoreInteractions(credentialRepository, jwtUtils);
    }
}
