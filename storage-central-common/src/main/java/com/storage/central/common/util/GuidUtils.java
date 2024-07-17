package com.storage.central.common.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GuidUtils {
    /**
     * Method to generate the GUID.
     *
     * @return the generated GUID in {@link String}.
     */
    public static String generateGuid() {
        return UUID.randomUUID().toString();
    }
}
