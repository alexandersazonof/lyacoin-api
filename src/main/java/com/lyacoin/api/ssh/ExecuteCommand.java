package com.lyacoin.api.ssh;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ExecuteCommand {

    public HashMap<SshCommand, String> bitcoin = new HashMap<>();

    public ExecuteCommand() {
        initBitcoinCommand();
    }

    private void initBitcoinCommand() {
        bitcoin.put(SshCommand.CREATE, "bitcoin-cli getnewaddress ");
    }
}
