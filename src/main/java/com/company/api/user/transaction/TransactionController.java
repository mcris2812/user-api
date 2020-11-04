package com.company.api.user.transaction;

import com.company.api.user.transaction.dto.TransactionResponse;
import com.company.api.user.transaction.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/transactions")
@AllArgsConstructor
@RestController
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionConverter transactionConverterConverter;

    @GetMapping
    @ResponseStatus(OK)
    public List<TransactionResponse> getTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        List<TransactionResponse> transactionsResponse = transactions.stream()
                .map(transactionConverterConverter::convert)
                .collect(toList());

        log.debug("All transactions {} ", transactionsResponse);
        return transactionsResponse;
    }

    @GetMapping("/{username}")
    @ResponseStatus(OK)
    public List<TransactionResponse> getTransactions(@Valid @PathVariable("username") @NotEmpty String username) {
        List<Transaction> transactions = transactionService.getUserTransactions(username);
        List<TransactionResponse> transactionsResponse = transactions.stream()
                .map(transactionConverterConverter::convert)
                .collect(toList());

        log.debug("All transactions {} ", transactionsResponse);
        return transactionsResponse;
    }
}
