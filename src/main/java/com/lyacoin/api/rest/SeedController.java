package com.lyacoin.api.rest;

import com.lyacoin.api.seed.SeedGenerator;
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

    @GetMapping
    public List<String> generateSeedPhrase() {
        return seedGenerator.generate();
    }
}
