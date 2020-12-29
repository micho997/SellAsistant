package pl.seller.assistant.databases;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.seller.assistant.databases.entity.TransactionEntity;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, Long> {

  List<TransactionEntity> findAllByOwner(String owner);
}
