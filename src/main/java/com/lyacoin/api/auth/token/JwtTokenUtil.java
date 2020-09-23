package com.lyacoin.api.auth.token;

import com.lyacoin.api.core.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenUtil {

    @Autowired
    private Key key;

    @Autowired
    private StandardPBEStringEncryptor encryptor;


    public String generateToken(User user) {
        return encryptor.encrypt(
                Jwts.builder()
                        .setSubject(user.getId())
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact()
        );
    }

    public String getIdFromToken(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(encryptor.decrypt(token));
    }
}
