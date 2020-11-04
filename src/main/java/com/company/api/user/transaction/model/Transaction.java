package com.company.api.user.transaction.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Document("transactions")
@Builder
@Data
public class Transaction {
    @Id
    private String id;
    private String externalId;
    private String accountId;
    private ExchangeRate exchangeRate;
    private OriginalAmount originalAmount;
    private Creditor creditor;
    private Debtor debtor;
    private String status;
    private String currency;
    private BigDecimal amount;
    private String update;
    private String description;

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class ExchangeRate {
        private String currencyFrom;
        private String currencyTo;
        private Double rate;
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class OriginalAmount {
        private BigDecimal amount;
        private String currency;
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class Creditor {
        private String maskedPan;
        private String name;
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class Debtor {
        private String maskedPan;
        private String name;
    }
}
