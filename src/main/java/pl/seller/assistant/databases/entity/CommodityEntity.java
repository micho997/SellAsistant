package pl.seller.assistant.databases.entity;

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

  CommodityEntity() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(BigDecimal currentPrice) {
    this.currentPrice = currentPrice;
  }

  public LocalDate getGotTime() {
    return gotTime;
  }

  public void setGotTime(LocalDate gotTime) {
    this.gotTime = gotTime;
  }

  public LocalDate getSoldTime() {
    return soldTime;
  }

  public void setSoldTime(LocalDate soldTime) {
    this.soldTime = soldTime;
  }

  public ImagesEntity getImageId() {
    return imageId;
  }

  public void setImageId(ImagesEntity imageId) {
    this.imageId = imageId;
  }

  public static final class CommodityEntityBuilder {

    private String producer;
    private BigDecimal price;
    private BigDecimal currentPrice;
    private LocalDate gotTime;
    private LocalDate soldTime;
    private ImagesEntity imageId;

    private CommodityEntityBuilder() {
    }

    public static CommodityEntityBuilder anCommodity() {
      return new CommodityEntityBuilder();
    }

    public CommodityEntityBuilder producer(String producer) {
      this.producer = producer;
      return this;
    }

    public CommodityEntityBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public CommodityEntityBuilder currentPrice(BigDecimal currentPrice) {
      this.currentPrice = currentPrice;
      return this;
    }

    public CommodityEntityBuilder gotTime(LocalDate gotTime) {
      this.gotTime = gotTime;
      return this;
    }

    public CommodityEntityBuilder soldTime(LocalDate soldTime) {
      this.soldTime = soldTime;
      return this;
    }

    public CommodityEntityBuilder imageId(ImagesEntity imageId) {
      this.imageId = imageId;
      return this;
    }

    public CommodityEntity build() {
      CommodityEntity commodityEntity = new CommodityEntity();
      commodityEntity.setProducer(producer);
      commodityEntity.setPrice(price);
      commodityEntity.setCurrentPrice(currentPrice);
      commodityEntity.setGotTime(gotTime);
      commodityEntity.setSoldTime(soldTime);
      commodityEntity.setImageId(imageId);
      return commodityEntity;
    }
  }
}
