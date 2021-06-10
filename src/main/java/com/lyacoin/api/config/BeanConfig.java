package com.lyacoin.api.config;

import com.lyacoin.api.auth.filters.ParseTokenFilter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Random;

@Configuration
public class BeanConfig {

    @Value("${jacypt.secret}")
    private String secret;

    @Autowired
    private ParseTokenFilter tokenFilter;


    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public FilterRegistrationBean<ParseTokenFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<ParseTokenFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();

        filterFilterRegistrationBean.setFilter(tokenFilter);
        filterFilterRegistrationBean.setUrlPatterns(Arrays.asList(
                "/accounts/*",
                "/sessions/*"
        ));
        return filterFilterRegistrationBean;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public StandardPBEStringEncryptor standardPBEStringEncryptor() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(secret);
        return standardPBEStringEncryptor;
    }

}
