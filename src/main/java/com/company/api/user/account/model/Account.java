package com.company.api.user.account.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Document("accounts")
@Builder
public class Account {
    @Id
    private String id;

    private String externalId;
    private LocalDateTime update;
    private String name;
    private String product;
    private AccountStatus status;
    private AccountType type;
    private BigDecimal balance;
    private String username;

    public void setId(String id) {
        this.id = id;
    }
}
