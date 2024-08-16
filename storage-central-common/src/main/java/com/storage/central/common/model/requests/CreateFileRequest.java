package com.storage.central.common.model.requests;

import lombok.Data;

@Data
public class CreateFileRequest {
    private String fileName;
    private String fileExtension;
    private String fileContent;
    private String path;
}
