package com.lyacoin.api.rest;

import com.lyacoin.api.core.model.InitSeed;
import com.lyacoin.api.seed.SeedGenerator;
import com.lyacoin.api.service.InitSeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("seeds")
public class SeedController {

    @Autowired
    private SeedGenerator seedGenerator;

    @Autowired
    private InitSeedService initSeedService;


    @GetMapping
    public List<String> generateSeedPhrase() {
        final List<String> seed = seedGenerator.generate();

        initSeedService.save(InitSeed.builder()
                .seed(seed)
                .build());

        return seed;
    }
}
