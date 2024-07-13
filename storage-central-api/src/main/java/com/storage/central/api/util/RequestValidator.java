package com.storage.central.api.util;

import com.storage.central.common.exceptions.InvalidRequestException;
import com.storage.central.common.model.requests.CreateUserRequest;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestValidator {
    /**
     * Method to validate the User Registration request. It will throw a {@link InvalidRequestException} in case its invalid.
     *
     * @param request: the request to be validated.
     */
    public static void validateRegisterUserRequest(final CreateUserRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidRequestException("Empty Request");
        }
        List<String> fields = Arrays.asList(request.getEmailId(), request.getFirstName(), request.getLastName(), request.getPassword());

        if (fields.stream().anyMatch(StringUtils::isEmpty)) {
            throw new InvalidRequestException("Invalid Request");
        }
    }
}
