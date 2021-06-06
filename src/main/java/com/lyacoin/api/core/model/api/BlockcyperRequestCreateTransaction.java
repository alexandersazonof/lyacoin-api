package com.lyacoin.api.core.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockcyperRequestCreateTransaction {

    private List<BlockcyperInputAddress> inputs;
    private List<BlockcyperOutputAddress> outputs;
    private String preference;


    @Data
    @AllArgsConstructor
    public static class BlockcyperInputAddress {
        private List<String> addresses;
    }

    @Data
    @AllArgsConstructor
    public static class BlockcyperOutputAddress {
        private List<String> addresses;
        private BigDecimal value;
    }
}
