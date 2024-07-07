package com.storage.central.common.model.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse {
    private String guid;
    private String jwtToken;
}
