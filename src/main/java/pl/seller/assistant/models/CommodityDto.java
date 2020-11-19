package pl.seller.assistant.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommodityDto {

  private Long id;
  private String producer;
  private BigDecimal price;
  private BigDecimal currentPrice;
  private LocalDate gotTime;
  private LocalDate soldTime;
  private Long imageId;

  CommodityDto() {

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

  public Long getImageId() {
    return imageId;
  }

  public void setImageId(Long imageId) {
    this.imageId = imageId;
  }

  public static final class CommodityDtoBuilder {

    private Long id;
    private String producer;
    private BigDecimal price;
    private BigDecimal currentPrice;
    private LocalDate gotTime;
    private LocalDate soldTime;
    private Long imageId;

    private CommodityDtoBuilder() {

    }

    public static CommodityDtoBuilder anCommodity() {
      return new CommodityDtoBuilder();
    }

    public CommodityDtoBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public CommodityDtoBuilder producer(String producer) {
      this.producer = producer;
      return this;
    }

    public CommodityDtoBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public CommodityDtoBuilder currentPrice(BigDecimal currentPrice) {
      this.currentPrice = currentPrice;
      return this;
    }

    public CommodityDtoBuilder gotTime(LocalDate gotTime) {
      this.gotTime = gotTime;
      return this;
    }

    public CommodityDtoBuilder soldTime(LocalDate soldTime) {
      this.soldTime = soldTime;
      return this;
    }

    public CommodityDtoBuilder imageId(Long imageId) {
      this.imageId = imageId;
      return this;
    }

    public CommodityDto build() {
      CommodityDto commodityDto = new CommodityDto();
      commodityDto.setId(id);
      commodityDto.setProducer(producer);
      commodityDto.setPrice(price);
      commodityDto.setCurrentPrice(currentPrice);
      commodityDto.setGotTime(gotTime);
      commodityDto.setSoldTime(soldTime);
      commodityDto.setImageId(imageId);
      return commodityDto;
    }
  }
}
