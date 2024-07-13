package com.storage.central.api.config;

import com.storage.central.api.controller.UserController;
import com.storage.central.api.repository.CredentialRepository;
import com.storage.central.api.repository.UserRepository;
import com.storage.central.api.service.UserService;
import com.storage.central.api.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.storage.central.api"})
public class ApiConfig {
    @Bean
    public UserService userService(@Autowired UserRepository userRepository, @Autowired CredentialRepository credentialRepository, @Autowired JwtUtils jwtUtils) {
        return new UserService(userRepository, credentialRepository, jwtUtils);
    }

    @Bean
    public JwtUtils jwtUtils(@Value("${jwt.secret.key}") String key) {
        return new JwtUtils(key);
    }
}
