package com.storage.central.api.util;

import com.storage.central.common.model.responses.CreateUserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {
    /**
     * Method to generate the Successful User registration response.
     *
     * @param guid: the guid generated for the user.
     * @param jwt:  the JWT for the user.
     * @return the {@link ResponseEntity} containing the user registration response for success API response.
     */
    public static ResponseEntity<CreateUserResponse> generateUserRegisterResponse(@NotNull final String guid, @NotNull final String jwt) {
        CreateUserResponse response = new CreateUserResponse();
        response.setGuid(guid);
        response.setJwtToken(jwt);
        return ResponseEntity.ok(response);
    }

    /**
     * Method to generate Failure Response.
     *
     * @param status:  the error message.
     * @param message: the {@link HttpStatus} code.
     * @return the ResponseEntity object with appropriate Error code.
     */
    public static ResponseEntity<?> generateFailureResponse(@NotNull final HttpStatus status, final String message) {
        return switch (status) {
            case BAD_REQUEST -> ResponseEntity.badRequest().body(message);
            case INTERNAL_SERVER_ERROR -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            default -> ResponseEntity.status(status).body(message);
        };
    }
}
