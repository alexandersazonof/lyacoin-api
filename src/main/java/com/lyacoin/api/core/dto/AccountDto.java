package com.lyacoin.api.core.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDto {

    private String name;
    private String address;
    private String currency;
    private BigDecimal balance;
    private BigDecimal equivalent;
}
