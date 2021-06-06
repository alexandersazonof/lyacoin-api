package com.lyacoin.api.core.request;

import com.lyacoin.api.core.Currency;
import lombok.Data;

@Data
public class AccountHistoryRequestItem {

    private String address;
    private Currency currency;
}
