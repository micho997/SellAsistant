package pl.seller.assistant.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_ID;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_USERNAME;
import static pl.seller.assistant.mother.TransactionMother.exampleTransactionWithEntry;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.mother.SameObjectChecker;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionServiceTest {

  @Autowired
  private TransactionService transactionService;

  @Test
  void should_save_transaction_in_database() {
    // then
    TransactionEntity transactionEntity = transactionService.save(exampleTransactionWithEntry(), EXAMPLE_USERNAME);

    // when
    SameObjectChecker.equalTransactionTransactionEntity(exampleTransactionWithEntry(), transactionEntity);
  }

  @Test
  void should_get_transaction_by_id() {
    // given
    TransactionEntity transactionEntity = transactionService.save(exampleTransactionWithEntry(), EXAMPLE_USERNAME);

    // then
    Optional<TransactionEntity> transactionFromDatabase = transactionService.getById(transactionEntity.getId());

    // when
    assertTrue(transactionFromDatabase.isPresent());
    assertEquals(transactionEntity.getId(), transactionFromDatabase.get().getId());
  }

  @Test
  void should_return_empty_if_id_does_not_exists() {
    // then
    Optional<TransactionEntity> transactionFromDatabase = transactionService.getById(EXAMPLE_ID);

    // when
    assertTrue(transactionFromDatabase.isEmpty());
  }
}
