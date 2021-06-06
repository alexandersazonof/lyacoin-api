package com.lyacoin.api.core.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlockcyperCreateAccount {
    private String address;
    @JsonProperty("public")
    private String publicKey;
    @JsonProperty("private")
    private String privateKey;
    private String wif;
}
