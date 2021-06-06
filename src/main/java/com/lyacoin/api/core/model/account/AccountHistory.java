package com.lyacoin.api.core.model.account;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountHistory {

    private String from;
    private String to;
    private BigDecimal value;
    private BigDecimal fees;
    private Integer confirmations;
    private String preference;
    private LocalDateTime confirmed;
    private LocalDateTime received;
    private String hash;
    private boolean spent;
    private String currency;
    private BigDecimal equivalent;

}
