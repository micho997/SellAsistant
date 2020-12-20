package pl.seller.assistant.mother;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.transaction.annotation.Transactional;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.SummaryEntity;
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
    assertNotNull(transactionEntity.getCommodities());
    assertNotNull(transactionDto.getCommodityIds());
  }

  public static void equalTransactionTransactionEntity(Transaction transaction, TransactionEntity transactionEntity) {
    assertEquals(transaction.getDate(), transactionEntity.getDate());
    assertEquals(transaction.getPrice(), transactionEntity.getPrice());
    assertEquals(transaction.getEarned(), transactionEntity.getEarned());
    assertEquals(transaction.getCommodities().size(), transactionEntity.getCommodities().size());
  }

  public static void equalTransactionTransactionDto(Transaction transaction, TransactionDto transactionDto) {
    assertEquals(transaction.getDate(), transactionDto.getDate());
    assertEquals(transaction.getPrice(), transactionDto.getPrice());
    assertEquals(transaction.getEarned(), transactionDto.getEarned());
    assertEquals(transaction.getCommodities().size(), transactionDto.getCommodityIds().size());
  }

  public static void equalCommodityCommodityEntity(Commodity commodity, CommodityEntity commodityEntity) {
    assertEquals(commodity.getProducer(), commodityEntity.getProducer());
    assertEquals(commodity.getPrice(), commodityEntity.getPrice());
    assertEquals(commodity.getCurrentPrice(), commodityEntity.getCurrentPrice());
    assertEquals(commodity.getGotTime(), commodityEntity.getGotTime());
    assertEquals(commodity.getSoldTime(), commodityEntity.getSoldTime());
    assertNotNull(commodity.getImages());
    assertNotNull(commodityEntity.getImageId());
  }

  public static void equalCommodityDtoCommodityEntity(CommodityDto commodityDto, CommodityEntity commodityEntity) {
    assertEquals(commodityDto.getProducer(), commodityEntity.getProducer());
    assertEquals(commodityDto.getPrice(), commodityEntity.getPrice());
    assertEquals(commodityDto.getCurrentPrice(), commodityEntity.getCurrentPrice());
    assertEquals(commodityDto.getGotTime(), commodityEntity.getGotTime());
    assertEquals(commodityDto.getSoldTime(), commodityEntity.getSoldTime());
    assertNotNull(commodityEntity.getImageId()
    );
  }

  public static void equalSummariesEntity(SummaryEntity summaryEntity1, SummaryEntity summaryEntity2) {
    assertEquals(summaryEntity1.getMonthOfYear(), summaryEntity2.getMonthOfYear());
    assertEquals(summaryEntity1.getOwner(), summaryEntity2.getOwner());
    assertEquals(summaryEntity1.getProfit(), summaryEntity2.getProfit());
    assertEquals(summaryEntity1.getCost(), summaryEntity2.getCost());
    assertEquals(summaryEntity1.getProfitMinusCost(), summaryEntity2.getProfitMinusCost());
    assertEquals(summaryEntity1.getBoughtCommodities(), summaryEntity2.getBoughtCommodities());
    assertEquals(summaryEntity1.getSoldCommodities(), summaryEntity2.getSoldCommodities());
    assertEquals(summaryEntity1.getMostPopularProducer(), summaryEntity2.getMostPopularProducer());
    assertEquals(summaryEntity1.getCommodityWithHighestPriceId(), summaryEntity2.getCommodityWithHighestPriceId());
    assertEquals(summaryEntity1.getCommodityWithHighestProfitId(), summaryEntity2.getCommodityWithHighestProfitId());
    assertEquals(summaryEntity1.getLastTransactionDate(), summaryEntity2.getLastTransactionDate());
  }
}
