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
public class CommodityDto {

  private Long id;
  private String producer;
  private BigDecimal oldPrice;
  private BigDecimal newPrice;
  private LocalDate gotTime;
  private LocalDate soldTime;
}
