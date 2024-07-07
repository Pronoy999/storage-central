package com.storage.central.utils;

import com.storage.central.common.exceptions.InvalidRequestException;
import com.storage.central.common.model.requests.CreateUserRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Validators {

    /**
     * Method to validate the Create User Request.
     * It will throw a {@link InvalidRequestException} if invalid request is passed.
     *
     * @param request: the request to be validated.
     */
    public static void validateCreateUserRequest(final CreateUserRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidRequestException("Empty Request");
        }
        List<String> fields = Arrays.asList(request.getEmail(), request.getFirstName(), request.getLastName(), request.getPassword());

        if (fields.stream().anyMatch(StringUtils::isEmpty)) {
            throw new InvalidRequestException("Invalid Request");
        }
    }
}
