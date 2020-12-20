package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;

import pl.seller.assistant.services.summary.Summary;

import java.math.BigDecimal;
import java.util.Collections;

public class SummaryMother {

  public static Summary exampleSummary() {
    return Summary.builder()
        .profit(BigDecimal.valueOf(5000))
        .cost(BigDecimal.valueOf(2000))
        .profitMinusCost(BigDecimal.valueOf(3000))
        .boughtCommodities(10)
        .soldCommodities(5)
        .mostPopularProducer("Nike")
        .commodityWithHighestPrice(CommodityMother.dto(CommodityMother.carlinSword(), 1L))
        .commodityWithHighestProfit(CommodityMother.dto(CommodityMother.pugSocks(), 2L))
        .lastTransactionDate(TransactionMother.dto(exampleTransaction(), Collections.singletonList(1L)))
        .mountOfYear("OCTOBER2020").build();
  }
}
