package com.lyacoin.api.task;

import com.lyacoin.api.service.InitSeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CheckInitSeedTask {

    @Autowired
    private InitSeedService initSeedService;

    @Scheduled(fixedDelay = 60000)
    public void doTask() {
        log.info("Init run task: check init seed task");
        List<String> ids = new ArrayList<>();

        initSeedService.findAll()
                .forEach(data -> {
                    if (data.getCreatedAt().plusHours(2).isBefore(LocalDateTime.now())) {
                        ids.add(data.getId());
                    }
                });

        log.info("Remove next init seed ids: " + ids);
        initSeedService.removeInIds(ids);
    }
}
