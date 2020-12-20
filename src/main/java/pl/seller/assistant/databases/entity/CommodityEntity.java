package pl.seller.assistant.databases.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "commodities")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommodityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private String producer;
  @Column(nullable = false)
  private BigDecimal price;
  @Column(name = "current_price", nullable = false)
  private BigDecimal currentPrice;
  @Column(name = "got_time", nullable = false)
  private LocalDate gotTime;
  @Column(name = "sold_time")
  private LocalDate soldTime;
  @OneToOne(fetch = FetchType.EAGER)
  private ImagesEntity imageId;
}
