package com.lyacoin.api.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClearCacheTask {

    private static List<String> cashes = Arrays.asList("history", "balance", "account");

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedDelay = 600000)
    public void reportCurrentTime() {
        cacheManager.getCacheNames().parallelStream().forEach(name -> {
            if (cashes.stream().anyMatch(i -> i.equals(name))) {
                log.info("Clear cache name: " + name);
                cacheManager.getCache(name).clear();
            }
        });
    }
}
