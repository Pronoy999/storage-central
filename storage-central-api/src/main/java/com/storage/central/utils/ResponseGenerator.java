package com.storage.central.utils;

import com.storage.central.common.model.responses.CreateUserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {
    /**
     * Method to generate Create User response.
     *
     * @param jwt:  The JWT for the user.
     * @param guid: The guid for the user.
     * @return the {@link CreateUserResponse}.
     */
    public static ResponseEntity<CreateUserResponse> generateCreateUserResponse(@NotNull final String jwt, @NotNull final String guid) {
        CreateUserResponse response = CreateUserResponse.builder().guid(guid).jwtToken(jwt).build();
        return ResponseEntity.ok(response);
    }
}
