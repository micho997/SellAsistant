package pl.seller.assistant.databases.entity;

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
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private LocalDate date;
  @Column(nullable = false)
  private BigDecimal price;
  @Column(nullable = false)
  private BigDecimal earned;
  @OneToMany(fetch = FetchType.EAGER)
  private List<CommodityEntity> commodities;

  public TransactionEntity() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getEarned() {
    return earned;
  }

  public void setEarned(BigDecimal earned) {
    this.earned = earned;
  }

  public List<CommodityEntity> getCommodities() {
    return commodities;
  }

  public void setCommodities(List<CommodityEntity> commodities) {
    this.commodities = commodities;
  }

  public static final class TransactionEntityBuilder {

    private LocalDate date;
    private BigDecimal price;
    private BigDecimal earned;
    private List<CommodityEntity> commodities;

    private TransactionEntityBuilder() {
    }

    public static TransactionEntityBuilder anTransaction() {
      return new TransactionEntityBuilder();
    }

    public TransactionEntityBuilder date(LocalDate date) {
      this.date = date;
      return this;
    }

    public TransactionEntityBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public TransactionEntityBuilder earned(BigDecimal earned) {
      this.earned = earned;
      return this;
    }

    public TransactionEntityBuilder commodities(List<CommodityEntity> commodities) {
      this.commodities = commodities;
      return this;
    }

    public TransactionEntity build() {
      TransactionEntity transactionEntity = new TransactionEntity();
      transactionEntity.setDate(date);
      transactionEntity.setPrice(price);
      transactionEntity.setEarned(earned);
      transactionEntity.setCommodities(commodities);
      return transactionEntity;
    }
  }
}
