package com.storage.central.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuidGeneratorTests {
    @Test
    public void testGenerateGuid() {
        String guid = GuidGenerator.generateGuid();
        Assertions.assertNotNull(guid);
    }
}
