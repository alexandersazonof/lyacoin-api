package com.lyacoin.api.config;

import com.lyacoin.api.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class GlobalCustomContext {

    public User user;

    public String getUserId() {
        return user == null ? null : user.getId();
    }
}
