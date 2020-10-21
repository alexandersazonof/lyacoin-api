package com.lyacoin.api.rest;

import com.lyacoin.api.config.GlobalCustomContext;
import com.lyacoin.api.core.Currency;
import com.lyacoin.api.core.dto.AccountDto;
import com.lyacoin.api.core.model.account.AccountBalance;
import com.lyacoin.api.core.request.RequestCreateAccount;
import com.lyacoin.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private GlobalCustomContext context;


    @GetMapping
    public List<AccountDto> findByUserId() {
        return accountService.findByUserId(context.user.getId());
    }

    @PostMapping("/{currency}")
    public String createCurrency(@PathVariable Currency currency,
                                 @RequestBody RequestCreateAccount request) {

        return currency.service().createAccount(request.getName());
    }

    @GetMapping("/{currency}/{address}/balance")
    public AccountBalance getAccountBalance(
            @PathVariable Currency currency,
            @PathVariable String address) {

        return currency.service().getBalance(address);
    }
}
