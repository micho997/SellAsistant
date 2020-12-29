package pl.seller.assistant.databases.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private LocalDate date;
  @Column(nullable = false)
  private BigDecimal price;
  @Column(nullable = false)
  private BigDecimal earned;
  @Column(nullable = false)
  private String owner;
  @OneToMany(fetch = FetchType.EAGER)
  private List<CommodityEntity> commodityEntities;
}
