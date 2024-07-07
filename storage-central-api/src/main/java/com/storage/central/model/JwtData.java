package com.storage.central.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtData {
    private String email;
    private String guid;
}
