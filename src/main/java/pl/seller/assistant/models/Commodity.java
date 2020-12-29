package pl.seller.assistant.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Commodity {

  private String producer;
  private BigDecimal oldPrice;
  private BigDecimal newPrice;
  private LocalDate gotTime;
  private LocalDate soldTime;
  private List<BufferedImage> images;
}
