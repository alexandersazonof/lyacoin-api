package com.lyacoin.api.core.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockcyperRequestConfirmTransaction {

    private Map tx;
    @JsonProperty("tosign")
    private List<String> signs;
    private List<String> signatures;
    @JsonProperty("pubkeys")
    private List<String> publicKeys;
}
