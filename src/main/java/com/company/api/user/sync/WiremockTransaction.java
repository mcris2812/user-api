package com.company.api.user.sync;

import com.company.api.user.transaction.model.Transaction;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WiremockTransaction {
    private String id;
    private String accountId;
    private Transaction.ExchangeRate exchangeRate;
    private Transaction.OriginalAmount originalAmount;
    private Transaction.Creditor creditor;
    private Transaction.Debtor debtor;
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