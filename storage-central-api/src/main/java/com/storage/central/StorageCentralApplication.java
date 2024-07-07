package com.storage.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.storage.central.common"})
public class StorageCentralApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageCentralApplication.class, args);
    }
}
