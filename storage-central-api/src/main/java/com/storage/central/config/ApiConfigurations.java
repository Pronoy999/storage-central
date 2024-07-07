package com.storage.central.config;

import com.storage.central.common.repository.CredentialRepository;
import com.storage.central.common.repository.UserRepository;
import com.storage.central.service.RegisterUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.storage.central.common"})
public class ApiConfigurations {
    @Bean
    public RegisterUserService registerUserService(UserRepository userRepository, CredentialRepository credentialRepository) {
        return new RegisterUserService(userRepository, credentialRepository);
    }
}
