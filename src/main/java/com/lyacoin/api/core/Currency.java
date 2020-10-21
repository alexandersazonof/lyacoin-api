package com.lyacoin.api.core;

import com.lyacoin.api.service.CurrencyService;
import com.lyacoin.api.service.impl.BitcoinCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

public enum Currency {
    BTC;

    @Component
    public static class CurrencyInject {

        @Autowired
        private BitcoinCurrencyService bitcoinService;

        @PostConstruct
        public void init() {
            EnumSet.allOf(Currency.class).forEach(item -> {
                switch (item) {
                    case BTC: {
                        item.setCurrencyService(bitcoinService);
                        break;
                    }
                }
            });
        }
    }

    private CurrencyService currencyService;

    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public CurrencyService service() {
        return currencyService;
    }
}
