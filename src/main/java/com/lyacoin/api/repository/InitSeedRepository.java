package com.lyacoin.api.repository;

import com.lyacoin.api.core.model.InitSeed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InitSeedRepository extends MongoRepository<InitSeed, String> {

    Optional<InitSeed> findBySeed(List<String> seed);

    void deleteAllByIdIn(List<String> ids);
}
