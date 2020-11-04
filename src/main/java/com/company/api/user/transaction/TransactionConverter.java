package com.company.api.user.transaction;

import com.company.api.user.transaction.dto.TransactionResponse;
import com.company.api.user.sync.WiremockTransaction;
import com.company.api.user.transaction.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//TODO: use mapStruct
@Mapper
public interface TransactionConverter {

    @Mapping(source = "externalId", target = "id")
    TransactionResponse convert(Transaction transaction);

    @Mapping(source = "id", target = "externalId")
    Transaction convertToTransaction(WiremockTransaction wiremockTransaction);
}
