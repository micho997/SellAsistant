package pl.seller.assistant.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.seller.assistant.mother.CommodityMother;

public class CommodityTest {

  @Test
  void shouldCreateCommodityObject() {
    Assertions.assertNotNull(CommodityMother.carlinSword());
    Assertions.assertNotNull(CommodityMother.pugSocks());
    Assertions.assertNotNull(CommodityMother.pumaHat());
  }
}
