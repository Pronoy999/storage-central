package com.storage.central.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.storage.central.api.controller", "com.storage.central.api.service"})
public class StorageCentralApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageCentralApplication.class, args);
        System.out.println("Hello world!");
    }
}