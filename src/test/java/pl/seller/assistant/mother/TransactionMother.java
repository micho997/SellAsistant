package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.CommodityMother.carlinSword;

import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.databases.entity.TransactionEntity.TransactionEntityBuilder;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.Transaction.TransactionBuilder;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.models.TransactionDto.TransactionDtoBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TransactionMother {

  public static Transaction exampleTransaction() {
    return TransactionBuilder.anTransaction()
        .date(LocalDate.of(2020, 10, 10))
        .price(BigDecimal.valueOf(1000))
        .earned(BigDecimal.valueOf(3000)).build();
  }

  public static Transaction exampleTransactionWithEntry() {
    Transaction transaction = exampleTransaction();
    transaction.setCommodities(Collections.singletonList(carlinSword()));
    return transaction;
  }

  public static TransactionDto dto(Transaction transaction, List<Long> commodityIds) {
    return TransactionDtoBuilder.anTransactionDto()
        .date(transaction.getDate())
        .price(transaction.getPrice())
        .earned(transaction.getEarned())
        .commoditiesIds(commodityIds).build();
  }

  public static TransactionEntity entity(Transaction transaction, List<CommodityEntity> commodityEntities) {
    return TransactionEntityBuilder.anTransactionEntity()
        .date(transaction.getDate())
        .price(transaction.getPrice())
        .earned(transaction.getEarned())
        .commoditiesIds(commodityEntities).build();
  }
}
