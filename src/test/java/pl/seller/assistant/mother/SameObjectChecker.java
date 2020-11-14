package pl.seller.assistant.mother;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.transaction.annotation.Transactional;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;

public class SameObjectChecker {

  @Transactional
  public static void equalTransactionDtoTransactionEntity(TransactionDto transactionDto, TransactionEntity transactionEntity) {
    assertEquals(transactionDto.getDate(), transactionEntity.getDate());
    assertEquals(transactionDto.getPrice(), transactionEntity.getPrice());
    assertEquals(transactionDto.getEarned(), transactionEntity.getEarned());
    assertNotNull(transactionEntity.getCommoditiesIds());
    assertNotNull(transactionDto.getCommoditiesIds());
  }

  public static void equalTransactionTransactionEntity(Transaction transaction, TransactionEntity transactionEntity) {
    assertEquals(transaction.getDate(), transactionEntity.getDate());
    assertEquals(transaction.getPrice(), transactionEntity.getPrice());
    assertEquals(transaction.getEarned(), transactionEntity.getEarned());
    assertEquals(transaction.getCommodities().size(), transactionEntity.getCommoditiesIds().size());
  }

  public static void equalTransactionTransactionDto(Transaction transaction, TransactionDto transactionDto) {
    assertEquals(transaction.getDate(), transactionDto.getDate());
    assertEquals(transaction.getPrice(), transactionDto.getPrice());
    assertEquals(transaction.getEarned(), transactionDto.getEarned());
    assertEquals(transaction.getCommodities().size(), transactionDto.getCommoditiesIds().size());
  }

  public static void equalCommodityCommodityEntity(Commodity commodity, CommodityEntity commodityEntity) {
    assertEquals(commodity.getProducer(), commodityEntity.getProducer());
    assertEquals(commodity.getPrice(), commodityEntity.getPrice());
    assertEquals(commodity.getCurrentPrice(), commodityEntity.getCurrentPrice());
    assertEquals(commodity.getGotTime(), commodityEntity.getGotTime());
    assertEquals(commodity.getSoldTime(), commodityEntity.getSoldTime());
    assertNotNull(commodity.getImages());
    assertNotNull(commodityEntity.getImagesId());
  }

  public static void equalCommodityDtoCommodityEntity(CommodityDto commodityDto, CommodityEntity commodityEntity) {
    assertEquals(commodityDto.getProducer(), commodityEntity.getProducer());
    assertEquals(commodityDto.getPrice(), commodityEntity.getPrice());
    assertEquals(commodityDto.getCurrentPrice(), commodityEntity.getCurrentPrice());
    assertEquals(commodityDto.getGotTime(), commodityEntity.getGotTime());
    assertEquals(commodityDto.getSoldTime(), commodityEntity.getSoldTime());
    assertNotNull(commodityEntity.getImagesId()
    );
  }
}
