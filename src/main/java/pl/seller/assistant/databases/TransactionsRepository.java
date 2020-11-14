package pl.seller.assistant.databases;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.seller.assistant.databases.entity.TransactionEntity;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, Long> {

}
