package com.company.api.user.transaction;

import com.company.api.user.transaction.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    Optional<Transaction> findByExternalId(String externalId);

    List<Transaction> findByAccountId(List<String> accountIds);

}
