package com.storage.central.test;

import com.storage.central.common.model.requests.CreateUserRequest;

public class TestDataGenerator {
    public static CreateUserRequest generateCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("email@email.com");
        createUserRequest.setPassword("password");
        createUserRequest.setFirstName("firstName");
        createUserRequest.setLastName("lastName");
        return createUserRequest;
    }
}
