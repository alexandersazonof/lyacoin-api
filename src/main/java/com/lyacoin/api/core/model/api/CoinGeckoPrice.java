package com.lyacoin.api.core.model.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinGeckoPrice {

    private FiatValue bitcoin;
    private FiatValue ethereum;
    private FiatValue dash;
    private FiatValue dogecoin;
    private FiatValue litecoin;

    @Data
    public static class FiatValue {
        private BigDecimal usd;
    }
}
