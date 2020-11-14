package pl.seller.assistant.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionDto {

  private Long id;
  private LocalDate date;
  private BigDecimal price;
  private BigDecimal earned;
  private List<Long> commoditiesIds;

  TransactionDto() {

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

  public List<Long> getCommoditiesIds() {
    return commoditiesIds;
  }

  public void setCommoditiesIds(List<Long> commoditiesIds) {
    this.commoditiesIds = commoditiesIds;
  }

  public static final class TransactionDtoBuilder {

    private Long id;
    private LocalDate date;
    private BigDecimal price;
    private BigDecimal earned;
    private List<Long> commoditiesIds;

    private TransactionDtoBuilder() {

    }

    public static TransactionDtoBuilder anTransactionDto() {
      return new TransactionDtoBuilder();
    }

    public TransactionDtoBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public TransactionDtoBuilder date(LocalDate date) {
      this.date = date;
      return this;
    }

    public TransactionDtoBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public TransactionDtoBuilder earned(BigDecimal earned) {
      this.earned = earned;
      return this;
    }

    public TransactionDtoBuilder commoditiesIds(List<Long> commoditiesIds) {
      this.commoditiesIds = commoditiesIds;
      return this;
    }

    public TransactionDto build() {
      TransactionDto transactionDto = new TransactionDto();
      transactionDto.setId(id);
      transactionDto.setDate(date);
      transactionDto.setPrice(price);
      transactionDto.setEarned(earned);
      transactionDto.setCommoditiesIds(commoditiesIds);
      return transactionDto;
    }
  }
}
