package com.lyacoin.api.core.model;

import lombok.Getter;

@Getter
public enum CurrencyCoin {

    BITCOIN("btc"),
    DOGE("doge"),
    LITECOIN("ltc"),
    DASH("dash");

    CurrencyCoin(String shortName) {
        this.shortName = shortName;
    }

    private final String shortName;
}
