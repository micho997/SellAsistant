package pl.seller.assistant.services;

import static pl.seller.assistant.databases.EntityMapper.toDto;
import static pl.seller.assistant.databases.EntityMapper.toEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.seller.assistant.databases.EntityMapper;
import pl.seller.assistant.databases.TransactionsRepository;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

  private final TransactionsRepository transactionsRepository;
  private final CommodityService commodityService;

  public TransactionService(TransactionsRepository transactionsRepository, CommodityService commodityService) {
    this.transactionsRepository = transactionsRepository;
    this.commodityService = commodityService;
  }

  @Transactional
  public TransactionDto save(Transaction transaction) {
    List<CommodityEntity> entities = transaction.getCommodities().stream()
        .map(commodityService::save)
        .collect(Collectors.toList());
    return toDto(transactionsRepository.save(toEntity(transaction, entities)));
  }

  @Transactional
  public Optional<TransactionDto> getById(Long id) {
    return transactionsRepository.findById(id)
        .map(EntityMapper::toDto);
  }

  public List<TransactionDto> getAll() {
    return transactionsRepository.findAll().stream()
        .map(EntityMapper::toDto)
        .collect(Collectors.toList());
  }

  public void updateEarned(Long transactionId, BigDecimal commoditySoldPrice) {
    Optional<TransactionEntity> transactionEntity = transactionsRepository.findById(transactionId);

    if (transactionEntity.isPresent()) {
      TransactionEntity updated = transactionEntity.get();

      updated.setEarned(commoditySoldPrice.plus());
      transactionsRepository.save(updated);
    } else {
      throw new NoSuchElementException("Can not find current transaction in database");
    }
  }
}
