package com.lyacoin.api.core.model.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BlockcyperBalance {
    private String address;
    private BigDecimal balance;
    private String error;
}
