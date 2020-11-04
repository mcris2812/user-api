package com.company.api.user.account;

import com.company.api.user.account.dto.AccountResponse;
import com.company.api.user.account.model.Account;
import org.springframework.stereotype.Service;

//TODO: use mapStruct
@Service
public class AccountConverter {

    public AccountResponse convert(Account account) {
        return AccountResponse.builder()
                .id(account.getExternalId())
                .update(account.getUpdate())
                .name(account.getName())
                .product(account.getProduct())
                .status(account.getStatus().name())
                .type(account.getType().name())
                .balance(account.getBalance())
                .build();
    }
}
