package com.lyacoin.api.core;

import com.lyacoin.api.service.CurrencyService;
import com.lyacoin.api.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.EnumSet;

public enum Currency {
    DASH("dash"),
    DOGE("dogecoin"),
    LITECOIN("litecoin"),
    BTC("bitcoin");

    Currency(String symbol) {
        this.symbol = symbol;
    }


    @Component
    public static class CurrencyInject {

        @Autowired
        private CoinGeckoPriceService coinGeckoPriceService;

        @Autowired
        private BitcoinCurrencyApiService bitcoinService;

        @Autowired
        private DashCurrencyService dashCurrencyService;

        @Autowired
        private DogecoinCurrencyService dogecoinCurrencyService;

        @Autowired
        private LitecoinCurrencyService litecoinCurrencyService;

        @PostConstruct
        public void init() {
            EnumSet.allOf(Currency.class).forEach(item -> {
                switch (item) {
                    case BTC: {
                        item.setPrice(coinGeckoPriceService.getPriceBySymbol(BTC.symbol));
                        item.setCurrencyService(bitcoinService);
                        break;
                    }

                    case DASH: {
                        item.setPrice(coinGeckoPriceService.getPriceBySymbol(DASH.symbol));
                        item.setCurrencyService(dashCurrencyService);
                        break;
                    }

                    case DOGE: {
                        item.setPrice(coinGeckoPriceService.getPriceBySymbol(DOGE.symbol));
                        item.setCurrencyService(dogecoinCurrencyService);
                        break;
                    }

                    case LITECOIN: {
                        item.setPrice(coinGeckoPriceService.getPriceBySymbol(LITECOIN.symbol));
                        item.setCurrencyService(litecoinCurrencyService);
                        break;
                    }
                }
            });
        }
    }

    private CurrencyService currencyService;
    private BigDecimal price;
    private String symbol;

    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public CurrencyService service() {
        return currencyService;
    }
}
