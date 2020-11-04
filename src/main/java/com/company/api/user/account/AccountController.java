package com.company.api.user.account;

import com.company.api.user.account.dto.AccountResponse;
import com.company.api.user.account.model.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;


@RequestMapping("/accounts")
@AllArgsConstructor
@RestController
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final AccountConverter accountConverter;

    @GetMapping
    @ResponseStatus(OK)
    public List<AccountResponse> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        List<AccountResponse> accountsResponse = accounts.stream()
                .map(accountConverter::convert)
                .collect(toList());

        log.debug("All accounts {} ", accountsResponse);
        return accountsResponse;
    }

    @GetMapping("/{username}")
    @ResponseStatus(OK)
    public List<AccountResponse> getAccounts(@Valid @PathVariable("username") @NotEmpty String username) {
        List<Account> accounts = accountService.getUserAccounts(username);
        List<AccountResponse> accountsResponse = accounts.stream()
                .map(accountConverter::convert)
                .collect(toList());

        log.debug("All {} accounts {} ", username, accountsResponse);
        return accountsResponse;
    }
}
