package com.lyacoin.api.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountUtils {

    private static final String ACCOUNT_NAME_DELIMITER = "-";

    public String createName(String userId, String name) {
        return userId + ACCOUNT_NAME_DELIMITER + name + ACCOUNT_NAME_DELIMITER + UUID.randomUUID().toString();
    }
}
