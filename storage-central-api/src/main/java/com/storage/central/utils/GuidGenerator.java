package com.storage.central.utils;

import java.util.UUID;

public class GuidGenerator {
    /**
     * Method to generate GUID.
     *
     * @return guid, if generated successfully.
     */
    public static String generateGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
