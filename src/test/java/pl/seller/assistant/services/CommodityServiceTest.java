package pl.seller.assistant.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.seller.assistant.mother.CommodityMother.carlinSword;
import static pl.seller.assistant.mother.SameObjectChecker.equalCommodityCommodityEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalCommodityDtoCommodityEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.CommodityDto;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommodityServiceTest {

  @Autowired
  private CommodityService commodityService;

  private Commodity exampleCommodity;

  @BeforeEach
  void setUp() {
    exampleCommodity = carlinSword();
  }

  @Test
  void should_save_commodity_in_database() {
    // then
    CommodityEntity entity = commodityService.save(exampleCommodity);

    // when
    equalCommodityCommodityEntity(exampleCommodity, entity);
  }

  @Test
  void should_get_commodity_from_database() {
    // given
    CommodityEntity saved = commodityService.save(exampleCommodity);

    // then
    Optional<CommodityDto> result = commodityService.getById(saved.getId());

    // when
    assertTrue(result.isPresent());
    equalCommodityDtoCommodityEntity(result.get(), saved);
  }
}
