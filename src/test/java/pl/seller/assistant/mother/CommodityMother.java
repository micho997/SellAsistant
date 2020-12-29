package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.ExampleData.EXAMPLE_ID;

import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.CommodityDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CommodityMother {

  public static Commodity carlinSword() {
    return Commodity.builder()
        .producer("Cipsoft")
        .oldPrice(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.CEILING))
        .newPrice(BigDecimal.valueOf(200.00).setScale(2, RoundingMode.CEILING))
        .gotTime(LocalDate.of(2020, 10, 10))
        .images(List.of()).build();
  }

  public static Commodity pugSocks() {
    return Commodity.builder()
        .producer("Nike")
        .oldPrice(BigDecimal.valueOf(5.00).setScale(2, RoundingMode.CEILING))
        .newPrice(BigDecimal.valueOf(20.00).setScale(2, RoundingMode.CEILING))
        .gotTime(LocalDate.of(2020, 10, 10))
        .images(List.of()).build();
  }

  public static Commodity pumaHat() {
    return Commodity.builder()
        .producer("Puma")
        .oldPrice(BigDecimal.valueOf(15.00).setScale(2, RoundingMode.CEILING))
        .newPrice(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.CEILING))
        .gotTime(LocalDate.of(2020, 10, 10))
        .images(List.of()).build();
  }

  public static CommodityDto dto(Commodity commodity) {
    return CommodityDto.builder()
        .id(EXAMPLE_ID)
        .producer(commodity.getProducer())
        .oldPrice(commodity.getOldPrice())
        .newPrice(commodity.getNewPrice())
        .gotTime(commodity.getGotTime())
        .soldTime(commodity.getSoldTime()).build();
  }

  public static CommodityEntity entity(Commodity commodity) {
    return CommodityEntity.builder()
        .id(EXAMPLE_ID)
        .producer(commodity.getProducer())
        .oldPrice(commodity.getOldPrice())
        .newPrice(commodity.getNewPrice())
        .gotTime(commodity.getGotTime())
        .soldTime(commodity.getSoldTime())
        .imagesEntity(ImagesEntity.builder()
            .id(EXAMPLE_ID).build()).build();
  }
}
