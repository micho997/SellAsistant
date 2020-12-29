package pl.seller.assistant.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionDto {

  private Long id;
  private LocalDate date;
  private BigDecimal price;
  private BigDecimal earned;
}
