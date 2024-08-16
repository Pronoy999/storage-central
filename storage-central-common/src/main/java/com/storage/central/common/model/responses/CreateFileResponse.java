package com.storage.central.common.model.responses;

import lombok.Data;

@Data
public class CreateFileResponse {
    private String fileName;
    private String fileGuid;
    private String filePath;
    private boolean isCreated;
}
