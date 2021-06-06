package com.lyacoin.api.core.model.account;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountBalance {
    private String address;
    private BigDecimal balance;
}
