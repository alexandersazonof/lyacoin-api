package com.lyacoin.api.core.request;

import com.lyacoin.api.core.model.HistoryType;
import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryRequest {

    private HistoryType type;
    private List<AccountHistoryRequestItem> accounts;
}
