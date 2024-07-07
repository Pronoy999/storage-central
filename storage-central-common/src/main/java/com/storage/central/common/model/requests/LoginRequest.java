package com.storage.central.common.model.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
