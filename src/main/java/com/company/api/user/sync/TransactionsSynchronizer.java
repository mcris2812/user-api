package com.company.api.user.sync;

import com.company.api.user.config.WiremockServiceConfig;
import com.company.api.user.transaction.TransactionConverter;
import com.company.api.user.transaction.TransactionService;
import com.company.api.user.transaction.model.Transaction;
import com.company.api.user.user.User;
import com.company.api.user.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionsSynchronizer {
    private final UserService userService;
    private final TransactionService transactionService;
    private final WiremockServiceConfig config;
    private final RestTemplate restTemplate;
    private final TransactionConverter transactionConverter;

    @Scheduled(cron = "0 0 8 * * *")
    public void sync() {
        log.info("Syncing transactions ...");

        userService.findAll()
                .forEach((user) -> {
                    log.info("Syncing {} transactions ...", user.getUsername());
                    Authentication authentication = authenticateUser(user);
                    WiremockTransaction[] wiremockTransactions = getWiremockTransactions(authentication);
                    saveTransactions(wiremockTransactions);
                });
    }

    private void saveTransactions(WiremockTransaction[] wiremockTransactions) {
        List<Transaction> transactions = Arrays.stream(wiremockTransactions)
                .map((transactionConverter::convertToTransaction))
                .collect(Collectors.toList());
        transactionService.saveAll(transactions);
    }

    private WiremockTransaction[] getWiremockTransactions(Authentication authentication) {
        HttpHeaders jwtHeaders = new HttpHeaders();
        jwtHeaders.add("X-AUTH", authentication.getToken());
        return restTemplate.exchange(this.config.getTransactionsUrl(), HttpMethod.GET, new HttpEntity<>(jwtHeaders), WiremockTransaction[].class).getBody();
    }

    private Authentication authenticateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("username", user.getUsername());
        return this.restTemplate
                .postForEntity(this.config.getLoginUrl(), new HttpEntity<>(headers), Authentication.class)
                .getBody();
    }

}
