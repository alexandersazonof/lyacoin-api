package com.lyacoin.api.service;

import com.lyacoin.api.core.model.account.AccountBalance;

public interface CurrencyService {

    String createAccount(String name);
    AccountBalance getBalance(String address);
}
