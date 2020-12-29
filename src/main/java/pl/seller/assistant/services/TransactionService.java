package pl.seller.assistant.services;

import static pl.seller.assistant.databases.EntityMapper.toEntity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.seller.assistant.databases.TransactionsRepository;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {

  private final TransactionsRepository transactionsRepository;
  private final CommodityService commodityService;

  @Transactional
  public TransactionEntity save(Transaction transaction, String username) {
    List<CommodityEntity> entities = transaction.getCommodities().stream()
        .map(commodityService::save)
        .collect(Collectors.toList());
    return transactionsRepository.save(toEntity(transaction, entities, username));
  }

  @Transactional
  public Optional<TransactionEntity> getById(Long id) {
    return transactionsRepository.findById(id);
  }

  public List<TransactionEntity> getAllForUser(String username) {
    return transactionsRepository.findAllByOwner(username);
  }

  public List<TransactionEntity> getByDate(String username, LocalDate from, LocalDate to) {
    List<TransactionEntity> result = new ArrayList<>();
    for (TransactionEntity transactionEntity : getAllForUser(username)) {
      if (transactionEntity.getDate().isAfter(from) && transactionEntity.getDate().isBefore(to)) {
        result.add(transactionEntity);
      }
    }
    return result;
  }
}
