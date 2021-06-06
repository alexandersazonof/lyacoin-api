package com.lyacoin.api.core.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BlockcyperResponseCreateTransaction {

    private Map tx;
    @JsonProperty("tosign")
    private List<String> signs;
}
