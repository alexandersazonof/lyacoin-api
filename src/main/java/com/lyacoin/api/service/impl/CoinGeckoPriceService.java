package com.lyacoin.api.service.impl;

import com.lyacoin.api.core.model.api.CoinGeckoHistory;
import com.lyacoin.api.core.model.api.CoinGeckoPrice;
import com.lyacoin.api.service.PriceService;
import com.lyacoin.api.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CoinGeckoPriceService implements PriceService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UrlUtils urlUtils;

    @Override
    public BigDecimal getPriceBySymbol(String symbol) {
        CoinGeckoPrice price = restTemplate.getForEntity(urlUtils.getLinkForPrice(symbol), CoinGeckoPrice.class).getBody();

        return getPrice(symbol, price);
    }

    @Override
    @CacheEvict(value = "price")
    public BigDecimal getPriceBySymbolAndDate(String symbol, LocalDateTime date) {
        return restTemplate.getForEntity(urlUtils.getHistoryData(symbol, date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))), CoinGeckoHistory.class)
                .getBody()
                .getData()
                .getPrice()
                .getUsd();
    }

    private BigDecimal getPrice(String symbol, CoinGeckoPrice coinGeckoPrice) {
        switch (symbol) {
            case "bitcoin" :
                return coinGeckoPrice.getBitcoin().getUsd();
            case "ethereum":
                return coinGeckoPrice.getEthereum().getUsd();
            case "litecoin":
                return coinGeckoPrice.getLitecoin().getUsd();
            case "dash":
                return coinGeckoPrice.getDash().getUsd();
            case "dogecoin":
                return coinGeckoPrice.getDogecoin().getUsd();
            default:
                return BigDecimal.ZERO;
        }
    }
}
