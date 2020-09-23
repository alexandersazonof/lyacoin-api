package com.lyacoin.api.config;

import com.lyacoin.api.auth.filters.ParseTokenFilter;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Collections;
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
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    public FilterRegistrationBean<ParseTokenFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<ParseTokenFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();

        filterFilterRegistrationBean.setFilter(tokenFilter);
        filterFilterRegistrationBean.setUrlPatterns(Collections.singletonList("/accounts/*"));
        return filterFilterRegistrationBean;
    }

    @Bean
    public StandardPBEStringEncryptor standardPBEStringEncryptor() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(secret);
        return standardPBEStringEncryptor;
    }

}
