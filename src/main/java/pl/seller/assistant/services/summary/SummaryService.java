package pl.seller.assistant.services.summary;

import org.springframework.stereotype.Service;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.services.CommodityService;
import pl.seller.assistant.services.TransactionService;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryService {

  private final CommodityService commodityService;
  private final TransactionService transactionService;
  private final SummaryHelper summaryHelper;

  public SummaryService(CommodityService commodityService, TransactionService transactionService,
      SummaryHelper summaryHelper) {
    this.commodityService = commodityService;
    this.transactionService = transactionService;
    this.summaryHelper = summaryHelper;
  }

  public Summary makeMonthlySummary(int year, Month month) {
    return makeSummaryForDate(
        LocalDate.of(year, month.minus(1), month.minus(1).maxLength()),
        LocalDate.of(year, month.plus(1), 1));
  }

  public Summary makeSummaryForDate(LocalDate from, LocalDate to) {
    List<TransactionDto> transaction = transactionService.getByDate(from, to);
    List<CommodityDto> commodities = getCommodities(transaction);
    return Summary.builder()
        .profit(summaryHelper.calculateProfit(transaction))
        .cost(summaryHelper.calculateCost(transaction))
        .profitMinusCost(summaryHelper.calculateProfitMinusCost(transaction))
        .boughtCommodities(summaryHelper.countBoughtCommodities(transaction))
        .soldCommodities(summaryHelper.countSoldCommodities(commodities))
        .mostPopularProducer(summaryHelper.findMostPopularProducer(commodities))
        .commodityHighestPrice(summaryHelper.getHighestPrice(commodities))
        .highestProfit(summaryHelper.getHighestProfit(commodities))
        .lastTransactionDate(summaryHelper.findLastTransactionDate(transaction)).build();
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
