package pl.seller.assistant.models;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Commodity {

  private List<BufferedImage> images;
  private String producer;
  private BigDecimal price;
  private BigDecimal currentPrice;
  private LocalDate gotTime;
  private LocalDate soldTime;

  Commodity() {
  }

  public List<BufferedImage> getImages() {
    return images;
  }

  public void setImages(List<BufferedImage> pictureId) {
    this.images = pictureId;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public BigDecimal getPrice() {
    return price.setScale(2, RoundingMode.HALF_DOWN);
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getCurrentPrice() {
    return currentPrice.setScale(2, RoundingMode.HALF_DOWN);
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

  public static final class CommodityBuilder {

    private List<BufferedImage> images;
    private String producer;
    private BigDecimal price;
    private BigDecimal currentPrice;
    private LocalDate gotTime;
    private LocalDate soldTime;

    public CommodityBuilder() {
    }

    public static CommodityBuilder anCommodity() {
      return new CommodityBuilder();
    }

    public CommodityBuilder images(List<BufferedImage> images) {
      this.images = images;
      return this;
    }

    public CommodityBuilder producer(String producer) {
      this.producer = producer;
      return this;
    }

    public CommodityBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public CommodityBuilder currentPrice(BigDecimal currentPrice) {
      this.currentPrice = currentPrice;
      return this;
    }

    public CommodityBuilder gotTime(LocalDate gotTime) {
      this.gotTime = gotTime;
      return this;
    }

    public CommodityBuilder soldTime(LocalDate soldTime) {
      this.soldTime = soldTime;
      return this;
    }

    public Commodity build() {
      Commodity commodity = new Commodity();
      commodity.setImages(images);
      commodity.setProducer(producer);
      commodity.setPrice(price);
      commodity.setCurrentPrice(currentPrice);
      commodity.setGotTime(gotTime);
      commodity.setSoldTime(soldTime);
      return commodity;
    }
  }
}
