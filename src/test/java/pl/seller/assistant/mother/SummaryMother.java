package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;

import pl.seller.assistant.services.summary.Summary;

import java.math.BigDecimal;

public class SummaryMother {

  public static Summary exampleSummary() {
    return Summary.builder()
        .profit(BigDecimal.valueOf(5000))
        .cost(BigDecimal.valueOf(2000))
        .profitMinusCost(BigDecimal.valueOf(3000))
        .boughtCommodities(10)
        .soldCommodities(5)
        .mostPopularProducer("Nike")
        .commodityWithHighestPrice(CommodityMother.dto(CommodityMother.carlinSword()))
        .commodityWithHighestProfit(CommodityMother.dto(CommodityMother.pugSocks()))
        .lastTransactionDate(TransactionMother.dto(exampleTransaction()))
        .mountOfYear("OCTOBER2020").build();
  }
}
