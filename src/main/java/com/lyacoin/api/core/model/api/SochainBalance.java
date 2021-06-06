package com.lyacoin.api.core.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SochainBalance {

    private String success;
    private Data data;

    @lombok.Data
    public static class Data {
        private String network;
        private String address;

        @JsonProperty("confirmed_balance")
        private BigDecimal confirmedBalance;
        @JsonProperty("unconfirmed_balance")
        private BigDecimal unconfirmedBalance;

    }
}
