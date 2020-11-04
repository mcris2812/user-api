package com.company.api.user.account;

import com.company.api.user.account.model.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        log.debug("Fetched {} ", accounts);
        return accounts;
    }

    public List<Account> getUserAccounts(String username) {
        List<Account> accounts = accountRepository.findByUsername(username);
        log.debug("Fetched {}  for {} ", accounts, username);
        return accounts;
    }

    public void saveAll(List<Account> accounts) {
        accounts.forEach((account) -> accountRepository
                .findByExternalId(account.getExternalId())
                .ifPresent((existingAccount) -> account.setId(existingAccount.getId()))

        );
        accountRepository.saveAll(accounts);
    }
}
