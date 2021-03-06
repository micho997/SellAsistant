package pl.seller.assistant.databases.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class CommodityEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private String producer;
  @Column(name = "old_price", nullable = false)
  private BigDecimal oldPrice;
  @Column(name = "new_price", nullable = false)
  private BigDecimal newPrice;
  @Column(name = "got_time", nullable = false)
  private LocalDate gotTime;
  @Column(name = "sold_time")
  private LocalDate soldTime;
  @OneToOne(fetch = FetchType.EAGER)
  private ImagesEntity imagesEntity;
}
