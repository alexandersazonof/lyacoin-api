package com.lyacoin.api.service;

import com.lyacoin.api.core.model.HistoryType;
import com.lyacoin.api.core.model.account.AccountBalance;
import com.lyacoin.api.core.model.account.AccountHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CurrencyService {

    String createAccount(String name);
    AccountBalance getBalance(String address);
    void createTransaction(String from, String to, BigDecimal value, String preference);
    List<AccountHistory> getHistory(String address, HistoryType type);
    BigDecimal getPriceByDate(LocalDateTime date);
    BigDecimal convertValue(BigDecimal value);
}
