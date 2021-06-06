package com.lyacoin.api.rest;

import com.lyacoin.api.config.GlobalCustomContext;
import com.lyacoin.api.core.Currency;
import com.lyacoin.api.core.dto.AccountDto;
import com.lyacoin.api.core.dto.AccountListResponse;
import com.lyacoin.api.core.model.RequestSendTx;
import com.lyacoin.api.core.model.account.AccountBalance;
import com.lyacoin.api.core.model.account.AccountHistory;
import com.lyacoin.api.core.request.AccountHistoryRequest;
import com.lyacoin.api.core.request.RequestCreateAccount;
import com.lyacoin.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("accounts")
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private GlobalCustomContext context;


    @GetMapping
    public AccountListResponse findByUserId() {
        List<AccountDto> accounts = accountService.findByUserId(context.user.getId());
        Double balance = accounts.stream()
                .map(AccountDto::getEquivalent)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();

        return AccountListResponse.builder()
                .accounts(accounts)
                .totalBalance(balance)
                .build();
    }

    @PutMapping("/history")
    public List<AccountHistory> getHistory(@RequestBody AccountHistoryRequest historyRequests) {
        return accountService.getHistory(historyRequests);
    }

    @PostMapping("/{currency}")
    public void createCurrency(@PathVariable Currency currency,
                                 @RequestBody RequestCreateAccount request) {

        currency.service().createAccount(request.getName());
    }

    @GetMapping("/{currency}/{address}/balance")
    public AccountBalance getAccountBalance(
            @PathVariable Currency currency,
            @PathVariable String address) {

        return currency.service().getBalance(address);
    }

    @PostMapping("/{currency}/send")
    public void sendTx(@PathVariable Currency currency,
                       @RequestBody RequestSendTx requestSendTx) {
        currency.service().createTransaction(
                requestSendTx.getFrom(),
                requestSendTx.getTo(),
                requestSendTx.getValue(),
                requestSendTx.getPreference()
        );
    }

    @GetMapping("/course/{currency}/{value}")
    public BigDecimal convertCryptoCurrency(
            @PathVariable Currency currency,
            @PathVariable BigDecimal value) {
        return currency.service().convertValue(value);
    }
}
