package pl.seller.assistant.services;

import static java.util.stream.Collectors.toList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.seller.assistant.databases.EntityMapper;
import pl.seller.assistant.databases.SummaryRepository;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.SummaryEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.services.summary.Summary;
import pl.seller.assistant.services.summary.SummaryHelper;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SummaryService {

  private final CommodityService commodityService;
  private final TransactionService transactionService;
  private final SummaryHelper summaryHelper;
  private final SummaryRepository summaryRepository;

  public SummaryEntity getMonthlySummary(LocalDate monthOfYear, String owner) {
    Optional<SummaryEntity> optionalSummaryEntity = getByIdAndOwner(createSummaryId(monthOfYear.getMonth(), monthOfYear.getYear()), owner);
    return optionalSummaryEntity.orElseGet(() -> makeMonthlySummary(monthOfYear, owner));
  }

  public SummaryEntity makeMonthlySummary(LocalDate monthOfYear, String owner) {
    Summary summary = makeSummaryForDate(owner,
        LocalDate.of(monthOfYear.getYear(), monthOfYear.getMonth().minus(1), monthOfYear.getMonth().minus(1).maxLength()),
        LocalDate.of(monthOfYear.getYear(), monthOfYear.getMonth().plus(1), 1));
    setCurrentMonth(summary, createSummaryId(monthOfYear.getMonth(), monthOfYear.getYear()));
    return saveMonthlySummary(summary, owner);
  }

  public Summary makeSummaryForDate(String username, LocalDate from, LocalDate to) {
    List<TransactionEntity> transactionEntities = transactionService.getByDate(username, from, to);
    List<TransactionDto> transactions = transactionEntities.stream()
        .map(EntityMapper::toDto)
        .collect(toList());
    List<CommodityDto> commodities = getCommodities(transactionEntities).stream()
        .map(EntityMapper::toDto)
        .collect(toList());
    return createSummary(transactions, commodities);
  }

  public Optional<SummaryEntity> getByDateAndOwner(int year, Month month, String owner) {
    return getByIdAndOwner(createSummaryId(month, year), owner);
  }

  public Optional<SummaryEntity> getByIdAndOwner(String monthOfYear, String owner) {
    return summaryRepository.findAllById(Collections.singleton(monthOfYear)).stream()
        .filter(summaryEntity -> summaryEntity.getOwner().equals(owner))
        .findFirst();
  }

  private Summary createSummary(List<TransactionDto> transactions, List<CommodityDto> commodities) {
    return Summary.builder()
        .profit(summaryHelper.calculateProfit(transactions))
        .cost(summaryHelper.calculateCost(transactions))
        .profitMinusCost(summaryHelper.calculateProfitMinusCost(transactions))
        .boughtCommodities(summaryHelper.countBoughtCommodities(commodities))
        .soldCommodities(summaryHelper.countSoldCommodities(commodities))
        .mostPopularProducer(summaryHelper.findMostPopularProducer(commodities))
        .commodityWithHighestPrice(summaryHelper.getCommodityWithHighestPrice(commodities))
        .commodityWithHighestProfit(summaryHelper.getCommodityWithHighestProfit(commodities))
        .lastTransactionDate(summaryHelper.findLastTransactionDate(transactions)).build();
  }

  private SummaryEntity saveMonthlySummary(Summary summary, String owner) {
    return summaryRepository.save(EntityMapper.toEntity(summary, owner));
  }

  private void setCurrentMonth(Summary summary, String mountOfYear) {
    summary.setMountOfYear(mountOfYear);
  }

  private String createSummaryId(Month month, int year) {
    return month.toString() + year;
  }

  private List<CommodityEntity> getCommodities(List<TransactionEntity> transactions) {
    return transactions.stream()
        .map(TransactionEntity::getCommodityEntities)
        .flatMap(List::stream)
        .map(CommodityEntity::getId)
        .map(commodityService::getById)
        .map(Optional::get)
        .collect(toList());
  }
}
