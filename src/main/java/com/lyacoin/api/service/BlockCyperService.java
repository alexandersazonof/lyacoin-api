package com.lyacoin.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.Lists;
import com.lyacoin.api.config.GlobalCustomContext;
import com.lyacoin.api.core.Currency;
import com.lyacoin.api.core.model.HistoryType;
import com.lyacoin.api.core.model.User;
import com.lyacoin.api.core.model.account.Account;
import com.lyacoin.api.core.model.account.AccountBalance;
import com.lyacoin.api.core.model.account.AccountHistory;
import com.lyacoin.api.core.model.api.*;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.exception.ExceptionMessageClient;
import com.lyacoin.api.service.impl.CoinGeckoPriceService;
import com.lyacoin.api.utils.SignUtils;
import com.lyacoin.api.utils.UrlUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BlockCyperService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UrlUtils urlUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GlobalCustomContext context;

    @Autowired
    private SignUtils signUtils;

    @Autowired
    private CoinGeckoPriceService coinGeckoPriceService;

    private final static BigDecimal SATOSHIS_VALUE = BigDecimal.valueOf(100000000);

    public String createAccount(String name, String currency) {
        BlockcyperCreateAccount account = restTemplate.postForEntity(urlUtils.getLinkForCreateAccount(currency), HttpEntity.EMPTY, BlockcyperCreateAccount.class).getBody();

        if (account == null) {
            throw new AppException(ExceptionMessageClient.CAN_NOT_CREATE_ACCOUNT, "Error because account is null");
        }
        accountService.save(Account.builder()
                .user(User.builder().id(context.user.getId()).build())
                .name(name)
                .publicKey(account.getPublicKey())
                .currency(Currency.valueOf(currency.toUpperCase()))
                .address(account.getAddress())
                .privateKey(account.getPrivateKey())
                .wif(account.getWif())
                .build());

        return account.getAddress();
    }

    @CacheEvict(value = "balance", allEntries = true)
    public AccountBalance getBalance(String address, String currency) {
        BlockcyperBalance balance = restTemplate.getForEntity(urlUtils.getLinkForBalanceInfo(address, currency), BlockcyperBalance.class).getBody();
        if (balance == null || balance.getError() != null) {
            throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Problem with getting balance: " + balance);
        }
        return AccountBalance.builder()
                .address(address)
                .balance(balance.getBalance())
                .build();
    }

    @CacheEvict(value = "history", allEntries = true)
    public List<AccountHistory> getHistory(String address, String currency, HistoryType type) {
        BlockcyperHistory history = restTemplate.getForEntity(urlUtils.getLinkForFullHistory(address, currency), BlockcyperHistory.class).getBody();

        if (history == null) {
            throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Got history response is nul");
        }

        return history.getTransactions().stream()
                .filter(i -> {
                    if (type.value.equals("All")) {
                        return true;
                    }

                    boolean isSpent = i.getOutputs().stream()
                            .findFirst()
                            .orElseThrow(() -> new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Can not find output data"))
                            .getAddresses()
                            .stream()
                            .anyMatch(address::equals);

                    if (isSpent && type.value.equals("Send")) {
                        return true;
                    }

                    if (!isSpent && type.value.equals("Receive")) {
                        return true;
                    }

                    return false;
                })
                .map(i -> {
                    BlockcyperHistory.BlockcyperHistoryItem.BlockcyperHistoryItemOutput output = i.getOutputs().stream()
                            .findFirst()
                            .orElseThrow(() -> new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Can not find output data"));

                    BlockcyperHistory.BlockcyperHistoryItem.BlockcyperHistoryItemInput input = i.getInputs().stream()
                            .findFirst()
                            .orElseThrow(() -> new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Can not find input data"));

                    BigDecimal amount = output.getValue().divide(SATOSHIS_VALUE);
                    BigDecimal fees = i.getFees().divide(SATOSHIS_VALUE);

                    return AccountHistory.builder()
                            .confirmations(i.getConfirmations())
                            .confirmed(i.getConfirmed())
                            .received(i.getReceived())
                            .fees(fees)
                            .preference(i.getPreference())
                            .hash(i.getHash())
                            .value(amount)
                            .currency(currency)
                            .spent(
                                    output.getAddresses()
                                    .stream()
                                    .anyMatch(address::equals)
                            )
                            .to(
                                    output
                                    .getAddresses()
                                    .stream().findFirst()
                                    .orElseThrow(() -> new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Can not find address data"))
                            )
                            .from(
                                    input
                                            .getAddresses()
                                            .stream().findFirst()
                                            .orElseThrow(() -> new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Can not find address data"))
                            )
                            .equivalent(
                                    coinGeckoPriceService.getPriceBySymbolAndDate(currency, i.getReceived())
                                    .multiply(amount)
                                    .setScale(2, RoundingMode.CEILING)
                            )
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void createTransaction(String from, String to, BigDecimal value, String preference, String currency) {
        try {
            Account account = accountService.findByAddressAndUserId(from, context.getUserId())
                    .orElseThrow(() -> new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Incorrect address or not access for this address"));

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            String jsonForCreateTx = ow.writeValueAsString(BlockcyperRequestCreateTransaction.builder()
                    .inputs(
                            Lists.newArrayList(
                                    new BlockcyperRequestCreateTransaction.BlockcyperInputAddress(Lists.newArrayList(from))
                            ))
                    .outputs(
                            Lists.newArrayList(
                                    new BlockcyperRequestCreateTransaction.BlockcyperOutputAddress(Lists.newArrayList(to), value)
                            ))
                    .preference(preference)
                    .build()
            );

            BlockcyperResponseCreateTransaction responseCreateTransaction =
                    restTemplate.postForEntity(urlUtils.getLinkForCreateTx(currency), jsonForCreateTx, BlockcyperResponseCreateTransaction.class).getBody();

            if (responseCreateTransaction == null) {
                throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "During create tx, got null response");
            }

            List<String> signs = responseCreateTransaction.getSigns()
                    .stream()
                    .map(it -> signUtils.signBtc(it, account.getWif()))
                    .collect(Collectors.toList());


            String jsonForSendTx = ow.writeValueAsString(
                    BlockcyperRequestConfirmTransaction.builder()
                            .tx(responseCreateTransaction.getTx())
                            .signs(responseCreateTransaction.getSigns())
                            .signatures(signs)
                            .publicKeys(
                                    responseCreateTransaction.getSigns().stream()
                                            .map(i -> account.getPublicKey())
                                            .collect(Collectors.toList()))
//                                    Lists.newArrayList(account.getPublicKey()))
                            .build()
            );
            log.info("Prepare for send tx: " + jsonForSendTx);

            String result = restTemplate.postForEntity(urlUtils.getLinkForSendTx(currency), jsonForSendTx, String.class).getBody();

            log.info("Send tx: " + result);
        } catch (JsonProcessingException e) {
            throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Error when parse json");
        }
    }
}
