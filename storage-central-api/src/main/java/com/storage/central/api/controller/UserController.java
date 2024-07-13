package com.storage.central.api.controller;

import com.storage.central.api.service.UserService;
import com.storage.central.api.util.ResponseGenerator;
import com.storage.central.common.model.requests.CreateUserRequest;
import com.storage.central.common.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "UserController")
@RequestMapping(path = Constants.USER_REGISTER_API_PATH)
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registerUsers(@RequestBody final CreateUserRequest request) {
        try {
            return userService.registerUser(request);
        } catch (Exception e) {
            log.error("Exception occurred while registering user.", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
        }
    }
}
