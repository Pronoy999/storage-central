package com.storage.central.common.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtData {
    private String guid;
    private String emailId;
}
