package pl.seller.assistant.mother;

import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.CommodityEntity.CommodityEntityBuilder;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.Commodity.CommodityBuilder;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.CommodityDto.CommodityDtoBuilder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import javax.imageio.ImageIO;

public class CommodityMother {

  public static Commodity carlinSword() {
    try {
      return CommodityBuilder.anCommodity()
          .images(Collections.singletonList(ImageIO.read(new File("src/test/resources/pictures/carlin_sword.png"))))
          .producer("Cipsoft")
          .price(BigDecimal.valueOf(100.00))
          .currentPrice(BigDecimal.valueOf(200.00))
          .gotTime(LocalDate.of(2020, 10, 10)).build();
    } catch (IOException exception) {
      throw new ExceptionMother();
    }
  }

  public static Commodity pugSocks() {
    try {
      return CommodityBuilder.anCommodity()
          .images(Collections.singletonList(ImageIO.read(new File("src/test/resources/pictures/pug_socks.png"))))
          .producer("Nike")
          .price(BigDecimal.valueOf(5.00))
          .currentPrice(BigDecimal.valueOf(20.00))
          .gotTime(LocalDate.of(2020, 10, 10)).build();

    } catch (IOException exception) {
      throw new ExceptionMother();
    }
  }

  public static Commodity pumaHat() {
    try {
      return CommodityBuilder.anCommodity()
          .images(Collections.singletonList(ImageIO.read(new File("src/test/resources/pictures/puma_hat.png"))))
          .producer("Puma")
          .price(BigDecimal.valueOf(15.00))
          .currentPrice(BigDecimal.valueOf(50.00))
          .gotTime(LocalDate.of(2020, 10, 10)).build();
    } catch (IOException exception) {
      throw new ExceptionMother();
    }
  }

  public static CommodityDto dto(Commodity commodity, Long imagesId) {
    return CommodityDtoBuilder.anCommodityDto()
        .producer(commodity.getProducer())
        .price(commodity.getPrice())
        .currentPrice(commodity.getCurrentPrice())
        .gotTime(commodity.getGotTime())
        .soldTime(commodity.getSoldTime())
        .imagesIds(imagesId).build();
  }

  public static CommodityEntity entity(Commodity commodity, ImagesEntity imagesEntity) {
    return CommodityEntityBuilder.anCommodity()
        .producer(commodity.getProducer())
        .price(commodity.getPrice())
        .currentPrice(commodity.getCurrentPrice())
        .gotTime(commodity.getGotTime())
        .soldTime(commodity.getSoldTime())
        .imagesId(imagesEntity).build();
  }
}
