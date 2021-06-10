package com.lyacoin.api.service;

import com.lyacoin.api.core.dto.AccountDto;
import com.lyacoin.api.core.model.account.Account;
import com.lyacoin.api.core.model.account.AccountHistory;
import com.lyacoin.api.core.request.AccountHistoryRequest;
import com.lyacoin.api.repository.AccountRepository;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StandardPBEStringEncryptor encryptor;

    private final static BigDecimal SATOSHI_COUNT = BigDecimal.valueOf(100000000);
    private final static Integer MAX_LIMIT = 30;


    public List<AccountHistory> getHistory(AccountHistoryRequest history) {
        List<AccountHistory> result =  history.getAccounts().parallelStream()
                .map(i -> i.getCurrency()
                        .service()
                        .getHistory(i.getAddress(), history.getType()))
                .collect(ArrayList<AccountHistory>::new, List::addAll, List::addAll);

        result
                .sort(Comparator.comparing(AccountHistory::getReceived).reversed());

        return result.stream()
                .limit(MAX_LIMIT)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "account", allEntries = true)
    public List<AccountDto> findByUserId(String userId) {
         return accountRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertTo)
                .collect(Collectors.toList());
    }

    public Optional<Account> findByAddressAndUserId(String address, String userId) {
        return accountRepository.findByAddressAndUserId(address, userId);
    }

    public Account save(Account account) {
        account.setWif(encryptor.encrypt(account.getWif()));
        account.setPrivateKey(encryptor.encrypt(account.getPrivateKey()));

        return accountRepository.save(account);
    }


    private AccountDto convertTo(Account account) {
        BigDecimal balance = account.getCurrency()
                .service()
                .getBalance(account.getAddress())
                .getBalance()
                .divide(SATOSHI_COUNT);

        BigDecimal price = account.getCurrency()
                .getPrice();

        return AccountDto.builder()
                .address(account.getAddress())
                .currency(account.getCurrency().name())
                .name(account.getName())
                .balance(balance)
                .equivalent(balance.multiply(price))
                .build();
    }
}
