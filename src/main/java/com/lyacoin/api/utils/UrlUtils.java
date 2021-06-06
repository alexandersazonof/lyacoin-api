package com.lyacoin.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlUtils {

    @Value("${external.link.ctypto.create}")
    private String blockHyper;

    @Value("${external.link.crypto.market.info}")
    private String coingecko;

    @Value("${external.list.crypto.balance}")
    private String urlCryptoCurrencyBalance;

    public String getLinkForBalanceInfo(String address, String currency) {
        return String.format("%s%s/main/addrs/%s/balance", blockHyper, currency, address);
    }

    public String getLinkForPrice(String symbol) {
        return coingecko + "simple/price?ids=" + symbol + "&vs_currencies=usd";
    }

    public String getLinkForCreateAccount(String currency) {
        return String.format("%s%s/main/addrs", blockHyper, currency);
    }

    public String getLinkForCreateTx(String currency) {
        return String.format("%s%s/main/txs/new", blockHyper, currency);
    }

    public String getLinkForSendTx(String currency) {
        return String.format("%s%s/main/txs/send", blockHyper, currency);
    }

    public String getLinkForFullHistory(String address, String currency) {
        return String.format("%s%s/main/addrs/%s/full", blockHyper, currency, address);
    }

    public String getHistoryData(String currency, String date) {
        return String.format("%scoins/%s/history?date=%s", coingecko, currency, date);
    }
}
