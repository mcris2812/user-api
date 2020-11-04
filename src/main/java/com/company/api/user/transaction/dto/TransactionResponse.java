package com.company.api.user.transaction.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@NoArgsConstructor
@Data
public class TransactionResponse {
    private String id;
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
