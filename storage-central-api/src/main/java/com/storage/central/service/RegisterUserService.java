package com.storage.central.service;

import com.storage.central.common.exceptions.InvalidRequestException;
import com.storage.central.common.model.entity.Credentials;
import com.storage.central.common.model.entity.User;
import com.storage.central.common.model.requests.CreateUserRequest;
import com.storage.central.common.model.responses.CreateUserResponse;
import com.storage.central.common.repository.CredentialRepository;
import com.storage.central.common.repository.UserRepository;
import com.storage.central.utils.GuidGenerator;
import com.storage.central.utils.JwtHelper;
import com.storage.central.utils.ResponseGenerator;
import com.storage.central.utils.Validators;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    /**
     * Method to register a user.
     *
     * @param request: The request object.
     * @return the ResponseEntity.
     */
    public ResponseEntity<CreateUserResponse> registerUser(@NotNull final CreateUserRequest request) {
        try {
            Validators.validateCreateUserRequest(request);
            User user = getUserObject(request);
            Credentials credentials = getCredentials(request);
            userRepository.save(user);
            credentialRepository.save(credentials);
            return ResponseGenerator.generateCreateUserResponse(JwtHelper.createJwt(credentials.getEmail(), user.getGuid()),
                    user.getGuid());
        } catch (InvalidRequestException e) {
            log.error("Invalid Request.", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method to create the User Object.
     *
     * @param request: The request object.
     * @return the {@link User} object.
     */
    private User getUserObject(@NotNull final CreateUserRequest request) {
        User user = new User();
        user.setGuid(GuidGenerator.generateGuid());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return user;
    }

    /**
     * Method to create the credentials.
     *
     * @param request: the request object.
     * @return the {@link Credentials} object.
     */
    private Credentials getCredentials(@NotNull final CreateUserRequest request) {
        Credentials credentials = new Credentials();
        credentials.setEmail(request.getEmail());
        credentials.setPassword(request.getPassword());
        return credentials;
    }
}
