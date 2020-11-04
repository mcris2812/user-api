package com.company.api.user.sync;

import com.company.api.user.user.User;
import com.company.api.user.user.UserService;
import com.company.api.user.account.AccountRepository;
import com.company.api.user.account.model.Account;
import com.company.api.user.config.WiremockServiceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Iterator;
import java.util.List;

import static java.util.List.of;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Testcontainers
class AccountsSynchronizerIT {
    @Autowired
    private AccountsSynchronizer accountsSynchronizer;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private WiremockServiceConfig wiremockServiceConfig;

    @Container
    public static GenericContainer wiremock = new GenericContainer(DockerImageName.parse("mihaitatinta/wiremock-example:0.0.1"))
            .withExposedPorts(8080);

    @Test
    @DisplayName("Fetch remote accounts and sync them with the persisted ones")
    void sync() {
        when(wiremockServiceConfig.getAccountsUrl()).thenReturn("http://localhost:" + wiremock.getMappedPort(8080) + "/accounts");
        when(wiremockServiceConfig.getLoginUrl()).thenReturn("http://localhost:" + wiremock.getMappedPort(8080) + "/login");
        when(userService.findAll()).thenReturn(List.of(new User("123", "ionescu")));

        accountsSynchronizer.sync();

        verify(accountRepository).saveAll(argThat((ArgumentMatcher<Iterable<? extends Account>>) accounts -> {
            Iterator<? extends Account> iterator = accounts.iterator();
            Account expectedAccount = iterator.next();
            return expectedAccount.getName().equals("Account-ionescu") && !iterator.hasNext();
        }));
    }
}