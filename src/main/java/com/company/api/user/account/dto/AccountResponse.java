package com.company.api.user.account.dto;

import lombok.Builder;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@ToString
public class AccountResponse {
    private String id;
    private LocalDateTime update;
    private String name;
    private String product;
    private String status;
    private String type;
    private BigDecimal balance;


}
