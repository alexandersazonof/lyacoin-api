package com.lyacoin.api.task;

import com.lyacoin.api.core.Currency;
import com.lyacoin.api.service.impl.CoinGeckoPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

import static com.lyacoin.api.core.Currency.BTC;

@Component
@Slf4j
public class ReloadPriceInfoTask {

    @Autowired
    private CoinGeckoPriceService coinGeckoPriceService;


    @Scheduled(fixedDelay = 60000)
    public void doTask() {
        log.info("Set price for cryptocurrency");
        EnumSet.allOf(Currency.class).forEach(item -> {
            switch (item) {
                case BTC: {
                    item.setPrice(coinGeckoPriceService.getPriceBySymbol(BTC.getSymbol()));
                    break;
                }
                case DASH: {
                    item.setPrice(coinGeckoPriceService.getPriceBySymbol(Currency.DASH.getSymbol()));
                    break;
                }

                case DOGE: {
                    item.setPrice(coinGeckoPriceService.getPriceBySymbol(Currency.DOGE.getSymbol()));
                    break;
                }

                case LITECOIN: {
                    item.setPrice(coinGeckoPriceService.getPriceBySymbol(Currency.LITECOIN.getSymbol()));
                    break;
                }
            }
        });
    }
}
