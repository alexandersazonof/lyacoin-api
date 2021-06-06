package com.lyacoin.api.auth.token;

import com.lyacoin.api.core.model.User;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    @Autowired
    private StandardPBEStringEncryptor encryptor;


    public String generateToken(User user) {
        return generateSimpleToken(user);
    }

    public String getIdFromToken(String token) {
        return parseSimpleToken(token);
    }

    private String generateSimpleToken(User user) {
        return encryptor.encrypt(encryptor.encrypt(user.getId()));
    }

    private String parseSimpleToken(String token) {
        return encryptor.decrypt(encryptor.decrypt(token));
    }
}
