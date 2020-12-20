package pl.seller.assistant.services;

import static java.util.stream.Collectors.toList;

import lombok.AllArgsConstructor;
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

  private List<CommodityDto> getCommodities(List<TransactionDto> transactions) {
    return transactions.stream()
        .map(TransactionDto::getCommodityIds)
        .flatMap(List::stream)
        .map(commodityService::getById)
        .map(Optional::get)
        .collect(toList());
  }
}
