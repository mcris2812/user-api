package com.company.api.user.sync;

import com.company.api.user.user.UserService;
import com.company.api.user.account.AccountService;
import com.company.api.user.account.model.Account;
import com.company.api.user.account.model.AccountStatus;
import com.company.api.user.account.model.AccountType;
import com.company.api.user.config.WiremockServiceConfig;
import com.company.api.user.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AccountsSynchronizer {
    private final UserService userService;
    private final AccountService accountService;
    private final WiremockServiceConfig config;
    private final RestTemplate restTemplate;

    @Scheduled(cron = "0 0 8 * * *")
    public void sync() {
        log.info("Syncing accounts ...");

        userService.findAll()
                .forEach((user) -> {
                    log.info("Syncing {} accounts ...", user.getUsername());
                    Authentication authentication = authenticateUser(user);
                    WiremockAccount[] wiremockAccounts = getWiremockAccounts(authentication);
                    saveAccounts(user, wiremockAccounts);
                });
    }

    private void saveAccounts(User user, WiremockAccount[] wiremockAccounts) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendPattern("XX").toFormatter();
        List<Account> accounts = Arrays.stream(wiremockAccounts)
                .map((wiremockAccount -> Account.builder()
                        .externalId(wiremockAccount.getId())
                        .update(OffsetDateTime.parse(wiremockAccount.getUpdate(), formatter).toLocalDateTime())
                        .name(wiremockAccount.getName())
                        .product(wiremockAccount.getProduct())
                        .status(wiremockAccount.getStatus())
                        .type(wiremockAccount.getType())
                        .balance(wiremockAccount.getBalance())
                        .username(user.getUsername())
                        .build()))
                .collect(Collectors.toList());
        accountService.saveAll(accounts);
    }

    private WiremockAccount[] getWiremockAccounts(Authentication authentication) {
        HttpHeaders jwtHeaders = new HttpHeaders();
        jwtHeaders.add("X-AUTH", authentication.getToken());
        return restTemplate.exchange(this.config.getAccountsUrl(), HttpMethod.GET, new HttpEntity<>(jwtHeaders), WiremockAccount[].class).getBody();
    }

    private Authentication authenticateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("username", user.getUsername());
        return this.restTemplate
                .postForEntity(this.config.getLoginUrl(), new HttpEntity<>(headers), Authentication.class)
                .getBody();
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    private static class WiremockAccount {
        private String id;
        private String update;
        private String name;
        private String product;
        private AccountStatus status;
        private AccountType type;
        private BigDecimal balance;
    }
}
