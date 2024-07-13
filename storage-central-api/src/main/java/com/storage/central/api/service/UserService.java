package com.storage.central.api.service;

import com.storage.central.api.util.JwtUtils;
import com.storage.central.api.util.RequestValidator;
import com.storage.central.api.util.ResponseGenerator;
import com.storage.central.common.exceptions.InvalidRequestException;
import com.storage.central.common.model.entity.Credential;
import com.storage.central.common.model.entity.User;
import com.storage.central.common.model.requests.CreateUserRequest;
import com.storage.central.common.repository.CredentialsRepository;
import com.storage.central.common.repository.UserRepository;
import com.storage.central.common.util.GuidUtils;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("userService")
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;

    public UserService(UserRepository userRepository, CredentialsRepository credentialsRepository) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    /**
     * Method to register a user.
     *
     * @param request: the request object.
     * @return the ResponseEntity with appropriate Body and HttpStatus code.
     */
    public ResponseEntity<?> registerUser(@NotNull final CreateUserRequest request) {
        try {
            RequestValidator.validateRegisterUserRequest(request);
            String guid = GuidUtils.generateGuid();
            String jwt = JwtUtils.createJwt(request.getEmailId(), guid);
            User user = createUser(request, guid);
            Credential credential = createCredential(user, request.getEmailId(), request.getPassword());
            userRepository.save(user);
            credentialsRepository.save(credential);
            return ResponseGenerator.generateUserRegisterResponse(guid, jwt);
        } catch (InvalidRequestException e) {
            log.error("Invalid Request.", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, "Invalid Request.");
        }
    }

    /**
     * Method to create the User object
     *
     * @param request: the request.
     * @param guid:    the Guid generated.
     * @return the {@link User} object.
     */
    private User createUser(@NotNull final CreateUserRequest request, @NotNull final String guid) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGuid(guid);
        return user;
    }

    /**
     * Method to create the Credential Object.
     *
     * @param user:     the {@link User} object.
     * @param emailId:  the email id for the user.
     * @param password: the password for the user.
     * @return the {@link Credential} object.
     */
    private Credential createCredential(@NotNull final User user, @NotNull final String emailId, @NotNull final String password) {
        Credential credential = new Credential();
        credential.setUser(user);
        credential.setEmailId(emailId);
        credential.setPassword(password);
        credential.setIsActive(true);
        return credential;
    }
}