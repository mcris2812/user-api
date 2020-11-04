package com.company.api.user.account;

import com.company.api.user.account.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByExternalId(String externalId);

    List<Account> findByUsername(String username);
}
