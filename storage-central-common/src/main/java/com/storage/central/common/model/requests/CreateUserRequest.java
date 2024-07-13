package com.storage.central.common.model.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
}
