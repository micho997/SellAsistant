package pl.seller.assistant.databases.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monthly_summaries")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SummaryEntity {

  @Id
  @Column(nullable = false, name = "month_of_year")
  private String monthOfYear;
  @Column(nullable = false)
  private String owner;
  private BigDecimal profit;
  private BigDecimal cost;
  @Column(name = "profit_minus_cost")
  private BigDecimal profitMinusCost;
  @Column(name = "bought_commodities")
  private int boughtCommodities;
  @Column(name = "sold_commodities")
  private int soldCommodities;
  @Column(name = "most_popular_producer")
  private String mostPopularProducer;
  @Column(name = "commodity_with_highest_price")
  private Long commodityWithHighestPriceId;
  @Column(name = "commodity_with_highest_profit")
  private Long commodityWithHighestProfitId;
  @Column(name = "last_transaction_date")
  private LocalDate lastTransactionDate;
}
