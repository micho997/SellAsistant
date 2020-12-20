package pl.seller.assistant.services.summary;

import org.springframework.stereotype.Component;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.TransactionDto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SummaryHelper {

  public BigDecimal calculateProfit(List<TransactionDto> transactions) {
    return transactions.stream()
        .filter(transactionDto -> transactionDto.getEarned() != null)
        .map(TransactionDto::getEarned)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal calculateCost(List<TransactionDto> transactions) {
    return transactions.stream()
        .map(TransactionDto::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal calculateProfitMinusCost(List<TransactionDto> transactions) {
    return calculateProfit(transactions).subtract(calculateCost(transactions));
  }

  public int countBoughtCommodities(List<CommodityDto> commodities) {
    return commodities.size();
  }

  public int countSoldCommodities(List<CommodityDto> commodities) {
    return Math.toIntExact(commodities.stream()
        .filter(commodityDto -> commodityDto.getSoldTime() != null)
        .count());
  }

  @SuppressWarnings({"OptionalGetWithoutIsPresent", "ComparatorMethodParameterNotUsed"})
  public String findMostPopularProducer(List<CommodityDto> commodities) {
    Map<String, Integer> result = new HashMap<>();

    for (CommodityDto commodityDto : commodities) {
      if (result.containsKey(commodityDto.getProducer())) {
        result.put(commodityDto.getProducer(), result.get(commodityDto.getProducer()) + 1);
      } else {
        result.put(commodityDto.getProducer(), 1);
      }
    }
    return result.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
  }

  public CommodityDto getCommodityWithHighestPrice(List<CommodityDto> commodities) {
    return Collections.max(commodities, Comparator.comparing(CommodityDto::getPrice));
  }

  public CommodityDto getCommodityWithHighestProfit(List<CommodityDto> commodities) {
    return Collections.max(commodities, Comparator.comparing(CommodityDto::getCurrentPrice));
  }

  public TransactionDto findLastTransactionDate(List<TransactionDto> transactions) {
    return Collections.max(transactions, Comparator.comparing(TransactionDto::getDate));
  }
}
