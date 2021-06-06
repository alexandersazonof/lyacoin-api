package com.lyacoin.api.core.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlockcyperHistory {

    @JsonProperty("txs")
    private List<BlockcyperHistoryItem> transactions;


    @Data
    public static class BlockcyperHistoryItem {
        private String hash;
        private BigDecimal fees;
        private String preference;
        private LocalDateTime confirmed;
        private LocalDateTime received;
        private Integer confirmations;
        private String spent;
        private List<BlockcyperHistoryItemInput> inputs;
        private List<BlockcyperHistoryItemOutput> outputs;


        @Data
        public static class BlockcyperHistoryItemInput {
            private List<String> addresses;
        }

        @Data
        public static class BlockcyperHistoryItemOutput {
            private List<String> addresses;
            private BigDecimal value;
        }
    }
}
