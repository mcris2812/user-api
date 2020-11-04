package com.company.api.user.account;

import com.company.api.user.account.model.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static java.util.List.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    private AccountService testSubject;
    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testSubject = new AccountService(accountRepository);
    }

    @Test
    @DisplayName("Save or replace accounts")
    void saveAll() {
        Account newAccount = Account.builder()
                .username("ionescu")
                .externalId("123")
                .build();

        Account existingAccount = Account.builder()
                .username("ionescu")
                .externalId("456")
                .build();

        Account accountInDb = Account.builder()
                .username("ionescu")
                .externalId("456")
                .id("789")
                .build();

        when(accountRepository.findByExternalId("456")).thenReturn(Optional.of(accountInDb));

        testSubject.saveAll(of(newAccount, existingAccount));

        Assertions.assertThat(existingAccount.getId()).isEqualTo("789");
        verify(accountRepository).saveAll(of(newAccount, existingAccount));
    }
}