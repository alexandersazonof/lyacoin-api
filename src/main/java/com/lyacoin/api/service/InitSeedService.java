package com.lyacoin.api.service;

import com.lyacoin.api.core.model.InitSeed;
import com.lyacoin.api.repository.InitSeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InitSeedService {

    @Autowired
    private InitSeedRepository initSeedRepository;

    public InitSeed save(InitSeed initSeed) {
        return initSeedRepository.save(initSeed);
    }

    public Optional<InitSeed> findBySeed(List<String> seed) {
        return initSeedRepository.findBySeed(seed);
    }

    public List<InitSeed> findAll() {
        return initSeedRepository.findAll();
    }

    public void removeInIds(List<String> ids) {
        initSeedRepository.deleteAllByIdIn(ids);
    }
}
