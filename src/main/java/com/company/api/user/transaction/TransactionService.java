package com.company.api.user.transaction;

import com.company.api.user.account.model.Account;
import com.company.api.user.transaction.model.Transaction;
import com.company.api.user.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getUserTransactions(String username) {
        List<String> accountsIds = accountService.getUserAccounts(username).stream()
                .map(Account::getExternalId)
                .collect(Collectors.toList());

        return transactionRepository.findByAccountId(accountsIds);
    }

    public void saveAll(List<Transaction> transactions) {
        transactions.forEach((transaction) -> transactionRepository
                .findByExternalId(transaction.getExternalId())
                .ifPresent((existingAccount) -> transaction.setId(existingAccount.getId()))

        );
        transactionRepository.saveAll(transactions);
    }
}
