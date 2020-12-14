package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.CommodityMother.carlinSword;

import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TransactionMother {

  public static Transaction exampleTransaction() {
    return Transaction.builder()
        .date(LocalDate.of(2020, 10, 10))
        .price(BigDecimal.valueOf(1000).setScale(2, RoundingMode.CEILING))
        .earned(BigDecimal.valueOf(3000).setScale(2, RoundingMode.CEILING)).build();
  }

  public static Transaction exampleTransactionWithEntry() {
    Transaction transaction = exampleTransaction();
    transaction.setCommodities(Collections.singletonList(carlinSword()));
    return transaction;
  }

  public static TransactionDto dto(Transaction transaction, List<Long> commodityIds) {
    return TransactionDto.builder()
        .date(transaction.getDate())
        .price(transaction.getPrice())
        .earned(transaction.getEarned())
        .commodityIds(commodityIds).build();
  }

  public static TransactionEntity entity(Transaction transaction, List<CommodityEntity> commodityEntities) {
    return TransactionEntity.builder()
        .date(transaction.getDate())
        .price(transaction.getPrice())
        .earned(transaction.getEarned())
        .commodities(commodityEntities).build();
  }
}
