package com.storage.central.common.model.responses;

import lombok.Data;

@Data
public class ErrorResponse {
    private String errorMessage;
    private String errorCode;
}
