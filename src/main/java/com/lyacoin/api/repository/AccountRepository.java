package com.lyacoin.api.repository;

import com.lyacoin.api.core.model.account.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    List<Account> findAllByUserId(String id);
}
