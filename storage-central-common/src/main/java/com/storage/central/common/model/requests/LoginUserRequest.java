package com.storage.central.common.model.requests;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String emailId;
    private String password;
}
