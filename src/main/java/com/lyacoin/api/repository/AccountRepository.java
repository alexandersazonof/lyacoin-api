package com.lyacoin.api.repository;

import com.lyacoin.api.core.model.account.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    List<Account> findAllByUserId(String id);
    Optional<Account> findByAddressAndUserId(String address, String userId);
}
