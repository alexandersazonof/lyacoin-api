package com.lyacoin.api.service;

import com.lyacoin.api.core.dto.AccountDto;
import com.lyacoin.api.core.model.account.Account;
import com.lyacoin.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountDto> findByUserId(String userId) {
        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertTo)
                .collect(Collectors.toList());
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }


    private AccountDto convertTo(Account account) {
        return AccountDto.builder()
                .address(account.getAddress())
                .currency(account.getCurrency())
                .name(account.getClientName())
                .build();
    }
}
