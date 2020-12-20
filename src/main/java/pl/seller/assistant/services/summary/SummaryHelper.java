package pl.seller.assistant.services.summary;

import org.springframework.stereotype.Component;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.TransactionDto;

import java.math.BigDecimal;
import java.time.LocalDate;
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

  public int countBoughtCommodities(List<TransactionDto> transactions) {
    return transactions.stream()
        .map(TransactionDto::getCommodityIds)
        .map(List::size)
        .reduce(0, Integer::sum);
  }

  public int countSoldCommodities(List<CommodityDto> commodities) {
    return Math.toIntExact(commodities.stream()
        .filter(commodityDto -> commodityDto.getSoldTime() != null)
        .count());
  }

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

  public CommodityDto getHighestPrice(List<CommodityDto> commodities) {
    int index = -1;
    BigDecimal highestPrice = BigDecimal.ZERO;
    for (int i = 0; i < commodities.size(); i++) {
      if (commodities.get(i).getPrice().compareTo(highestPrice) > 0) {
        index = i;
        highestPrice = commodities.get(i).getCurrentPrice();
      }
    }
    return commodities.get(index);
  }

  public CommodityDto getHighestProfit(List<CommodityDto> commodities) {
    BigDecimal highestProfit = BigDecimal.ZERO;
    int index = -1;
    for (int i = 0; i < commodities.size(); i++) {
      if (commodities.get(i).getSoldTime() != null) {
        if (commodities.get(i).getCurrentPrice().subtract(commodities.get(i).getPrice()).compareTo(highestProfit) > 0) {
          index = i;
          highestProfit = commodities.get(i).getCurrentPrice().subtract(commodities.get(i).getPrice());
        }
      }
    }
    return commodities.get(index);
  }

  public LocalDate findLastTransactionDate(List<TransactionDto> transaction) {
    return transaction.stream()
        .map(TransactionDto::getDate)
        .max(LocalDate::compareTo).orElseThrow();
  }
}
