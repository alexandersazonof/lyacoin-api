package com.lyacoin.api.core.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinGeckoHistory {

    @JsonProperty("market_data")
    private CoinGeckoHistoryMarketData data;

    @Data
    public static class CoinGeckoHistoryMarketData {

        @JsonProperty("current_price")
        private CoinGeckoHistoryPrice price;

        @Data
        public static class CoinGeckoHistoryPrice {
            private BigDecimal usd;
        }
    }
}
