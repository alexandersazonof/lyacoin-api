package com.lyacoin.api.core.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestSendTx {

    private String from;
    private String to;
    private BigDecimal value;
    private String preference;
}
