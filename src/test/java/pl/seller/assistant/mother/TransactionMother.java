package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.CommodityMother.carlinSword;
import static pl.seller.assistant.mother.CommodityMother.pumaHat;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_ID;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_USERNAME;

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
    transaction.setCommodities(List.of(carlinSword()));
    return transaction;
  }

  public static TransactionDto dto(Transaction transaction) {
    return TransactionDto.builder()
        .id(EXAMPLE_ID)
        .date(transaction.getDate())
        .price(transaction.getPrice())
        .earned(transaction.getEarned()).build();
  }

  public static TransactionEntity entity(Transaction transaction) {
    return TransactionEntity.builder()
        .id(EXAMPLE_ID)
        .date(transaction.getDate())
        .price(transaction.getPrice())
        .earned(transaction.getEarned())
        .owner(EXAMPLE_USERNAME)
        .commodityEntities(Collections.singletonList(CommodityMother.entity(pumaHat()))).build();
  }
}
