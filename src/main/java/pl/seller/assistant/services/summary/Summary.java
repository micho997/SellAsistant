package pl.seller.assistant.services.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.seller.assistant.models.CommodityDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Summary {

  private BigDecimal profit;
  private BigDecimal cost;
  private BigDecimal profitMinusCost;
  private int boughtCommodities;
  private int soldCommodities;
  private String mostPopularProducer;
  private CommodityDto commodityHighestPrice;
  private CommodityDto highestProfit;
  private LocalDate lastTransactionDate;
}
