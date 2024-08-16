package com.storage.central.core.provider;

import org.springframework.stereotype.Service;

@Service
public class GitHubService implements StorageProviderService {

    @Override
    public String persistFile(String fileName, String fileExtension, String path, String base64EncodedContent) {
        return "";
    }
}
