package pl.seller.assistant.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Transaction {

  private LocalDate date;
  private BigDecimal price;
  private BigDecimal earned;
  private List<Commodity> commodities;
}
