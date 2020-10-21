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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Autowired
    private Key key;

    @Autowired
    private StandardPBEStringEncryptor encryptor;


    public String generateToken(User user) {
//        return encryptor.encrypt(
//                Jwts.builder()
//                        .setSubject(user.getId())
//                        .setExpiration(getExpireTime())
//                        .signWith(key, SignatureAlgorithm.HS256)
//                        .compact()
//        );
        return generateSimpleToken(user);
    }

    public String getIdFromToken(String token) {
//        return parseToken(token).getBody().getSubject();
        return parseSimpleToken(token);
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(encryptor.decrypt(token));
    }

    private String generateSimpleToken(User user) {
        return encryptor.encrypt(encryptor.encrypt(user.getId()));
    }

    private String parseSimpleToken(String token) {
        return encryptor.decrypt(encryptor.decrypt(token));
    }

    private Date getExpireTime() {
        LocalDateTime ldt = LocalDateTime.now().plusYears(2);
        return new Date(ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
