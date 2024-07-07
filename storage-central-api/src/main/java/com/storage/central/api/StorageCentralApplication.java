package com.storage.central.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StorageCentralApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageCentralApplication.class, args);
        System.out.println("Hello world!");
    }
}