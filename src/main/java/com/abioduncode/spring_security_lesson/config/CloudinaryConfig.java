package com.abioduncode.spring_security_lesson.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dcjf95rrw");
        config.put("api_key", "835747648543527");
        config.put("api_secret", "TSyI_npA_nRkbfNo10_OJ3bOVKg");
        return new Cloudinary(config);
    }
}