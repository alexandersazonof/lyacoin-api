package com.lyacoin.api.service.impl;

import com.lyacoin.api.core.model.CurrencyCoin;
import com.lyacoin.api.core.model.HistoryType;
import com.lyacoin.api.core.model.account.AccountBalance;
import com.lyacoin.api.core.model.account.AccountHistory;
import com.lyacoin.api.service.BlockCyperService;
import com.lyacoin.api.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DogecoinCurrencyService implements CurrencyService {

    @Autowired
    private BlockCyperService blockCyperService;

    @Autowired
    private CoinGeckoPriceService coinGeckoPriceService;

    private final static String SYMBOL = CurrencyCoin.DOGE.getShortName();


    @Override
    public String createAccount(String name) {
        return blockCyperService.createAccount(name, SYMBOL);
    }

    @Override
    public AccountBalance getBalance(String address) {
        return blockCyperService.getBalance(address, SYMBOL);
    }

    @Override
    public void createTransaction(String from, String to, BigDecimal value, String preference) {
        blockCyperService.createTransaction(from, to, value, preference, SYMBOL);
    }

    @Override
    public List<AccountHistory> getHistory(String address, HistoryType type) {
        return blockCyperService.getHistory(address, SYMBOL, type);
    }
    @Override
    public BigDecimal getPriceByDate(LocalDateTime date) {
        return coinGeckoPriceService.getPriceBySymbolAndDate(SYMBOL, date);
    }

    @Override
    public BigDecimal convertValue(BigDecimal value) {
        return coinGeckoPriceService.getPriceBySymbol(SYMBOL).multiply(value);
    }
}
