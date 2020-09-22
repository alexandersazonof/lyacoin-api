package com.lyacoin.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class BeanConfig {

    @Bean
    public Random random() {
        return new Random();
    }
}
