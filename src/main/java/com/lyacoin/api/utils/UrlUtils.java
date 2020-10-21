package com.lyacoin.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlUtils {

    @Value("${external.link.ctypto.first}")
    private String urlCryptoCurrencyInfo;


    public String getLinkForBalanceInfo(String address, String currency) {
        return urlCryptoCurrencyInfo + currency + "/main/addrs/" + address + "/balance";
    }
}
