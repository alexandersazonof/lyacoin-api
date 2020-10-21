package com.lyacoin.api.core.model.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalance {
    private String address;
    private BigDecimal balance;
}
