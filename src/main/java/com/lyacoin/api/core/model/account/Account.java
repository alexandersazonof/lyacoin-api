package com.lyacoin.api.core.model.account;

import com.lyacoin.api.core.Currency;
import com.lyacoin.api.core.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("account")
@Builder
public class Account {

    @Id
    private String id;

    @DBRef
    private User user;

    private Currency currency;
    private String name;
    private String publicKey;
    private String privateKey;
    private String wif;
    private String address;

    @CreatedDate
    private LocalDateTime createdAt;
}
