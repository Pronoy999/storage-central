package com.storage.central.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.storage.central.common","com.storage.central.api"})
//@EntityScan("com.storage.central.model.entity")
//@ComponentScan(basePackages = {"com.storage.central.common", "com.storage.central.api"})
@EnableJpaRepositories(basePackages = {"com.storage.central.common.repository"})
public class StorageCentralApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageCentralApplication.class, args);
        System.out.println("Hello world!");
    }
}