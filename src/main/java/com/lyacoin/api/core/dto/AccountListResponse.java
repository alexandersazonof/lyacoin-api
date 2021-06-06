package com.lyacoin.api.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountListResponse {

    private List<AccountDto> accounts;
    private Double totalBalance;
}
