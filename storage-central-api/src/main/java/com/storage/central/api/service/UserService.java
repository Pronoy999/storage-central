package com.storage.central.api.service;

import com.storage.central.api.entity.Credential;
import com.storage.central.api.entity.User;
import com.storage.central.api.repository.CredentialRepository;
import com.storage.central.api.repository.UserRepository;
import com.storage.central.api.util.JwtUtils;
import com.storage.central.api.util.RequestValidator;
import com.storage.central.api.util.ResponseGenerator;
import com.storage.central.common.exceptions.InvalidRequestException;
import com.storage.central.common.model.requests.CreateUserRequest;
import com.storage.central.common.model.requests.LoginUserRequest;
import com.storage.central.common.util.GuidUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
@Slf4j
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.storage.central.api"})
public class UserService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final JwtUtils jwtUtils;

    /**
     * Method to register the user.
     *
     * @param request: The API Request.
     * @return the Response Entity according to the response.
     */
    public ResponseEntity<?> registerUser(@NotNull final CreateUserRequest request) {
        try {
            RequestValidator.validateRegisterUserRequest(request);
            if (checkIfUserAlreadyExists(request)) {
                return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, "User Already exists");
            }
            String guid = GuidUtils.generateGuid();
            String jwt = jwtUtils.createJwt(request.getEmailId(), guid);
            User user = createUser(request, guid);
            Credential credential = createCredential(request, guid);
            userRepository.save(user);
            credentialRepository.save(credential);
            return ResponseGenerator.generateUserRegisterResponse(guid, jwt);
        } catch (InvalidRequestException e) {
            log.error("Invalid request.", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, "Invalid Request.");
        }
    }

    /**
     * Method to login users.
     *
     * @param request: The API request.
     * @return the GUID and JWT if valid credentials, else return 400.
     */
    public ResponseEntity<?> loginUsers(@NotNull final LoginUserRequest request) {
        try {
            RequestValidator.validateLoginUserRequest(request);
            List<Credential> credentials = credentialRepository.findByEmailIdAndPasswordAndIsActive(request.getEmailId(), request.getPassword(), true);
            if (credentials.isEmpty()) {
                return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, "Invalid Credentials!");
            }
            Credential credential = credentials.get(0);
            String jwt = jwtUtils.createJwt(credential.getEmailId(), credential.getUserGuid());
            return ResponseGenerator.generateUserRegisterResponse(credential.getUserGuid(), jwt);
        } catch (InvalidRequestException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Method to check whether the user exists or not.
     *
     * @param request: the API request.
     * @return true, if the email id is not registered, else false.
     */
    private boolean checkIfUserAlreadyExists(@NotNull final CreateUserRequest request) {
        List<Credential> users = credentialRepository.findByEmailId(request.getEmailId());
        return !users.isEmpty();
    }

    /**
     * Method to create the user object.
     *
     * @param request: the API Request.
     * @param guid:    The guid generated for the user.
     * @return the {@link User} object.
     */
    private User createUser(@NotNull final CreateUserRequest request, @NotNull final String guid) {
        User user = new User();
        user.setGuid(guid);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return user;
    }

    /**
     * Method to create the credential object.
     *
     * @param request: The API Request.
     * @param guid:    The guid generated for the user.
     * @return the {@link Credential} object.
     */
    private Credential createCredential(@NotNull final CreateUserRequest request, @NotNull final String guid) {
        Credential credential = new Credential();
        credential.setId(guid);
        credential.setUserGuid(guid);
        credential.setPassword(request.getPassword());
        credential.setEmailId(request.getEmailId());
        credential.setActive(true);
        return credential;
    }
}