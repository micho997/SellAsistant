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
import pl.seller.assistant.services.summary.Summary;

public class SameObjectChecker {

  @Transactional
  public static void equalTransactionDtoTransactionEntity(TransactionDto transactionDto, TransactionEntity transactionEntity) {
    assertEquals(transactionDto.getDate(), transactionEntity.getDate());
    assertEquals(transactionDto.getPrice(), transactionEntity.getPrice());
    assertEquals(transactionDto.getEarned(), transactionEntity.getEarned());
    assertNotNull(transactionEntity.getCommodityIds());
    assertNotNull(transactionDto.getCommodityIds());
  }

  public static void equalTransactionTransactionEntity(Transaction transaction, TransactionEntity transactionEntity) {
    assertEquals(transaction.getDate(), transactionEntity.getDate());
    assertEquals(transaction.getPrice(), transactionEntity.getPrice());
    assertEquals(transaction.getEarned(), transactionEntity.getEarned());
    assertEquals(transaction.getCommodities().size(), transactionEntity.getCommodityIds().size());
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

  public static void equalSummarySummaryEntity(Summary summary, SummaryEntity summaryEntity) {
    assertEquals(summary.getProfit(), summaryEntity.getProfit());
    assertEquals(summary.getCost(), summaryEntity.getCost());
    assertEquals(summary.getProfitMinusCost(), summaryEntity.getProfitMinusCost());
    assertEquals(summary.getBoughtCommodities(), summaryEntity.getBoughtCommodities());
    assertEquals(summary.getSoldCommodities(), summaryEntity.getSoldCommodities());
    assertEquals(summary.getMostPopularProducer(), summaryEntity.getMostPopularProducer());
    assertEquals(summary.getCommodityWithHighestPrice().getId(), summaryEntity.getCommodityWithHighestPriceId());
    assertEquals(summary.getCommodityWithHighestProfit().getId(), summaryEntity.getCommodityWithHighestProfitId());
    assertEquals(summary.getLastTransactionDate().getId(), summaryEntity.getLastTransactionDateId());
    assertEquals(summary.getMountOfYear(), summaryEntity.getMonthOfYear());
  }
}
