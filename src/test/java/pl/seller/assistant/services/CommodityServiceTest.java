package pl.seller.assistant.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.seller.assistant.mother.CommodityMother.carlinSword;
import static pl.seller.assistant.mother.CommodityMother.pugSocks;
import static pl.seller.assistant.mother.CommodityMother.pumaHat;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_ID;
import static pl.seller.assistant.mother.SameObjectChecker.equalCommodityCommodityEntity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.models.Commodity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommodityServiceTest {

  @Autowired
  private CommodityService commodityService;

  @Test
  void should_save_commodity_in_database() {
    // then
    CommodityEntity entity = commodityService.save(pumaHat());

    // when
    equalCommodityCommodityEntity(pumaHat(), entity);
  }

  @Test
  void should_get_commodity_from_database() {
    // given
    CommodityEntity saved = commodityService.save(pugSocks());

    // then
    Optional<CommodityEntity> commodityFromDatabase = commodityService.getById(saved.getId());

    // when
    assertTrue(commodityFromDatabase.isPresent());
    assertEquals(saved.getId(), commodityFromDatabase.get().getId());
  }

  @Test
  void should_return_empty_if_id_does_not_exists() {
    // then
    Optional<CommodityEntity> commodityFromDatabase = commodityService.getById(EXAMPLE_ID);

    // when
    assertTrue(commodityFromDatabase.isEmpty());
  }

  @Test
  void should_get_commodities_by_gotTime() {
    // given
    for (Commodity commodity : take10Correct5IncorrectGotTimeCommodities()) {
      commodityService.save(commodity);
    }
    int expectedSize = 10;

    // when

    List<CommodityEntity> result = commodityService
        .getByGotTime(LocalDate.of(2020, 1, 31), LocalDate.of(2020, 3, 1));

    // then
    assertEquals(expectedSize, result.size());
  }

  @Test
  void should_get_commodities_by_soldTime() {
    // given
    for (Commodity commodity : take10Correct5NotSold5IncorrectSoldTimeCommodities()) {
      commodityService.save(commodity);
    }
    int expectedSize = 10;

    // when
    List<CommodityEntity> result = commodityService
        .getBySoldTime(LocalDate.of(2020, 1, 31), LocalDate.of(2020, 3, 1));

    // then
    assertEquals(expectedSize, result.size());
  }

  private List<Commodity> take10Correct5IncorrectGotTimeCommodities() {
    List<Commodity> commodities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Commodity commodity = pugSocks();
      commodity.setGotTime(LocalDate.of(2020, 2, 10 + i));
      commodities.add(commodity);
    }
    for (int i = 0; i < 5; i++) {
      Commodity commodity = pumaHat();
      commodity.setGotTime(LocalDate.of(2020, 3, 10 + i));
      commodities.add(commodity);
    }
    return commodities;
  }

  private List<Commodity> take10Correct5NotSold5IncorrectSoldTimeCommodities() {
    List<Commodity> commodities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Commodity commodity = pugSocks();
      commodity.setSoldTime(LocalDate.of(2020, 2, 10 + i));
      commodities.add(commodity);
    }
    for (int i = 0; i < 5; i++) {
      Commodity commodity = pumaHat();
      commodity.setSoldTime(LocalDate.of(2020, 3, 10 + i));
      commodities.add(commodity);
    }
    for (int i = 0; i < 5; i++) {
      commodities.add(carlinSword());
    }
    return commodities;
  }
}
