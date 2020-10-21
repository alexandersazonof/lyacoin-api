package com.lyacoin.api.core.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("init-seed")
@Data
@Builder
public class InitSeed {

    @Id
    private String id;
    private List<String> seed;

    @CreatedDate
    private LocalDateTime createdAt;
}
