package com.storage.central.api.util;

import com.storage.central.common.model.requests.CreateFileRequest;
import com.storage.central.common.model.responses.CreateFileResponse;
import com.storage.central.common.model.responses.CreateUserResponse;
import com.storage.central.common.model.responses.ErrorResponse;
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
     * Method to generate the response of successfully creating the file.
     *
     * @param fileName: the file Name
     * @param filePath: the path of the file.
     * @param fileId:   the guid for the file.
     * @return the ResponseEntity with HTTP 200.
     */
    public static ResponseEntity<CreateFileResponse> generateSuccessfulFileCreationResponse(@NotNull final String fileName, @NotNull final String filePath, @NotNull final String fileId) {
        CreateFileResponse response = new CreateFileResponse();
        response.setCreated(true);
        response.setFileName(fileName);
        response.setFilePath(filePath);
        response.setFileGuid(fileId);
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
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(status.toString());
        response.setErrorMessage(message);
        return switch (status) {
            case BAD_REQUEST -> ResponseEntity.badRequest().body(response);
            case INTERNAL_SERVER_ERROR -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            case UNAUTHORIZED -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            default -> ResponseEntity.status(status).body(response);
        };
    }
}
