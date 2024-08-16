package com.storage.central.core.provider;

import jakarta.validation.constraints.NotNull;

public interface StorageProviderService {
    public String persistFile(@NotNull final String fileName, @NotNull final String fileExtension, @NotNull final String path,@NotNull final String base64EncodedContent);
}
