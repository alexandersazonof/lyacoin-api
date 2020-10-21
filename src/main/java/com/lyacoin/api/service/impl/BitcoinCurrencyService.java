package com.lyacoin.api.service.impl;

import com.lyacoin.api.config.GlobalCustomContext;
import com.lyacoin.api.core.model.User;
import com.lyacoin.api.core.model.account.Account;
import com.lyacoin.api.core.model.account.AccountBalance;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.exception.ExceptionMessageClient;
import com.lyacoin.api.service.AccountService;
import com.lyacoin.api.service.CurrencyService;
import com.lyacoin.api.ssh.ExecuteCommand;
import com.lyacoin.api.ssh.RemoteMachine;
import com.lyacoin.api.ssh.SshCommand;
import com.lyacoin.api.utils.AccountUtils;
import com.lyacoin.api.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class BitcoinCurrencyService implements CurrencyService {

    @Autowired
    private UrlUtils urlUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RemoteMachine remoteMachine;

    @Autowired
    private ExecuteCommand executeCommand;

    @Autowired
    private GlobalCustomContext context;

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private AccountService accountService;



    @Value("${ssh.bitcoin.ip}")
    private String ip;

    @Value("${ssh.bitcoin.username}")
    private String username;

    @Value("${ssh.bitcoin.password}")
    private String password;


    private final static String BITCOIN_SYMBOL = "btc";
    private final static String NEW_LINE = "\n";


    @Override
    @Transactional
    public String createAccount(String name) {
        try {
            String fullName = accountUtils.createName(context.user.getId(), name);

            String command = executeCommand.bitcoin.get(SshCommand.CREATE) + fullName;
            String address = remoteMachine.execute(ip, username, password, command).replaceAll(NEW_LINE, "");

            log.info("Address created: " + address);
            Account account = accountService.save(
                    Account.builder()
                            .address(address)
                            .clientName(name)
                            .currency(BITCOIN_SYMBOL)
                            .name(fullName)
                            .user(User.builder().id(context.user.getId()).build())
                            .build()
            );

            log.info("Created new bitcoin account: " + account);
            return address;
        } catch (Exception e) {
            log.error("Error during execute btc command to : " + ip, e);
            throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Error in ssh connection");
        }
    }

    @Override
    public AccountBalance getBalance(String address) {
        return restTemplate
                .getForEntity(urlUtils.getLinkForBalanceInfo(address, BITCOIN_SYMBOL), AccountBalance.class)
                .getBody();
    }
}
