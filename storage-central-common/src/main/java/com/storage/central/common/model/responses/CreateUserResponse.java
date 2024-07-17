package com.storage.central.common.model.responses;

import lombok.Data;

@Data
public class CreateUserResponse {
    private String guid;
    private String jwtToken;
}
