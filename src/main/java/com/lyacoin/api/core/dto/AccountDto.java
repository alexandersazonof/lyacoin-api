package com.lyacoin.api.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    private String name;
    private String address;
    private String currency;
}
