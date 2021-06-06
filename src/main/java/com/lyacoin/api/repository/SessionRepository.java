package com.lyacoin.api.repository;

import com.lyacoin.api.core.model.session.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {

    Optional<Session> findByTokenAndUserId(String token, String userId);
}
