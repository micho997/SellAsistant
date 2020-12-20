package pl.seller.assistant.services;

import org.springframework.stereotype.Service;
import pl.seller.assistant.databases.EntityMapper;
import pl.seller.assistant.databases.SummaryRepository;
import pl.seller.assistant.databases.entity.SummaryEntity;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.services.summary.Summary;
import pl.seller.assistant.services.summary.SummaryHelper;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SummaryService {

  private final CommodityService commodityService;
  private final TransactionService transactionService;
  private final SummaryHelper summaryHelper;
  private final SummaryRepository summaryRepository;

  public SummaryService(CommodityService commodityService, TransactionService transactionService,
      SummaryHelper summaryHelper, SummaryRepository summaryRepository) {
    this.commodityService = commodityService;
    this.transactionService = transactionService;
    this.summaryHelper = summaryHelper;
    this.summaryRepository = summaryRepository;
  }

  public SummaryEntity makeMonthlySummary(int year, Month month, String owner) {
    Summary summary = makeSummaryForDate(
        LocalDate.of(year, month.minus(1), month.minus(1).maxLength()),
        LocalDate.of(year, month.plus(1), 1));
    setCurrentMonth(summary, createSummaryId(month, year));
    return saveMonthlySummary(summary, owner);
  }

  public Summary makeSummaryForDate(LocalDate from, LocalDate to) {
    List<TransactionDto> transactions = transactionService.getByDate(from, to);
    List<CommodityDto> commodities = getCommodities(transactions);
    return createSummary(transactions, commodities);
  }

  public Optional<SummaryEntity> getByDateAndOwner(int year, Month month, String owner) {
    return getByIdAndOwner(createSummaryId(month, year), owner);
  }

  public Optional<SummaryEntity> getByIdAndOwner(String monthOfYear, String owner) {
    List<SummaryEntity> summaryEntities = summaryRepository.findAllById(Collections.singleton(monthOfYear));
    for (SummaryEntity summaryEntity : summaryEntities) {
      if (owner.equals(summaryEntity.getOwner())) {
        return Optional.of(summaryEntity);
      }
    }
    return Optional.empty();
  }

  private Summary createSummary(List<TransactionDto> transactions, List<CommodityDto> commodities) {
    return Summary.builder()
        .profit(summaryHelper.calculateProfit(transactions))
        .cost(summaryHelper.calculateCost(transactions))
        .profitMinusCost(summaryHelper.calculateProfitMinusCost(transactions))
        .boughtCommodities(summaryHelper.countBoughtCommodities(transactions))
        .soldCommodities(summaryHelper.countSoldCommodities(commodities))
        .mostPopularProducer(summaryHelper.findMostPopularProducer(commodities))
        .commodityHighestPrice(summaryHelper.getHighestPrice(commodities))
        .highestProfit(summaryHelper.getHighestProfit(commodities))
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

  private List<CommodityDto> getCommodities(List<TransactionDto> transaction) {
    List<CommodityDto> commodities = new ArrayList<>();
    for (TransactionDto transactionDto : transaction) {
      for (Long id : transactionDto.getCommodityIds()) {
        commodityService.getById(id).ifPresent(commodities::add);
      }
    }
    return commodities;
  }
}
