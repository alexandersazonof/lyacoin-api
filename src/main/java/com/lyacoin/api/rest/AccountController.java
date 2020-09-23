package com.lyacoin.api.rest;

import com.lyacoin.api.config.GlobalCustomContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    private GlobalCustomContext customContext;

    @GetMapping
    public String getAccounts() {
        return customContext.getUserId();
    }
}
