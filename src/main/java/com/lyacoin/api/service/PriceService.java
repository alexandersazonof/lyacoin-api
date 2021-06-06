package com.lyacoin.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PriceService {
    BigDecimal getPriceBySymbol(String symbol);
    BigDecimal getPriceBySymbolAndDate(String symbol, LocalDateTime date);

}
