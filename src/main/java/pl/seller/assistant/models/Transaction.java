package pl.seller.assistant.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Transaction {

  private LocalDate date;
  private BigDecimal price;
  private BigDecimal earned;
  private List<Commodity> commodities;

  Transaction() {
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public BigDecimal getPrice() {
    return price.setScale(2, RoundingMode.HALF_DOWN);
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getEarned() {
    return earned.setScale(2, RoundingMode.HALF_DOWN);
  }

  public void setEarned(BigDecimal earned) {
    this.earned = earned;
  }

  public List<Commodity> getCommodities() {
    return commodities;
  }

  public void setCommodities(List<Commodity> commodities) {
    this.commodities = commodities;
  }

  public static final class TransactionBuilder {

    private LocalDate date;
    private BigDecimal price;
    private BigDecimal earned;
    private List<Commodity> commodities;

    private TransactionBuilder() {
    }

    public static TransactionBuilder anTransaction() {
      return new TransactionBuilder();
    }

    public TransactionBuilder date(LocalDate date) {
      this.date = date;
      return this;
    }

    public TransactionBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public TransactionBuilder earned(BigDecimal earned) {
      this.earned = earned;
      return this;
    }

    public TransactionBuilder commodities(List<Commodity> commodities) {
      this.commodities = commodities;
      return this;
    }

    public Transaction build() {
      Transaction transaction = new Transaction();
      transaction.setDate(date);
      transaction.setPrice(price);
      transaction.setEarned(earned);
      transaction.setCommodities(commodities);
      return transaction;
    }
  }
}
