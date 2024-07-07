package com.storage.central.service;

import com.storage.central.model.requests.CreateUserRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {
    public void registerUser(@NotNull final CreateUserRequest request) {

    }
}
