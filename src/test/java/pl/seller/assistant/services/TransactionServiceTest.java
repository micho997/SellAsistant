package pl.seller.assistant.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.seller.assistant.mother.CommodityMother.pumaHat;
import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.mother.SameObjectChecker;

import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionServiceTest {

  @Autowired
  private TransactionService transactionService;

  private Transaction exampleTransaction;

  @BeforeEach
  void setUp() {
    exampleTransaction = exampleTransaction();
    exampleTransaction.setCommodities(Collections.singletonList(pumaHat()));
  }

  @Test
  void should_save_transaction_in_database() {
    // then
    TransactionDto transactionDto = transactionService.save(exampleTransaction);

    // when
    SameObjectChecker.equalTransactionTransactionDto(exampleTransaction, transactionDto);
  }

  @Test
  void should_get_transaction_by_id() {
    // given
    TransactionDto transactionDto1 = transactionService.save(exampleTransaction);

    // then
    Optional<TransactionDto> transactionDto2 = transactionService.getById(transactionDto1.getId());

    // when
    assertTrue(transactionDto2.isPresent());
    assertEquals(transactionDto1.getId(), transactionDto2.get().getId());
  }
}
