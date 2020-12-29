package pl.seller.assistant.mother;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    assertEquals(transactionDto.getId(), transactionEntity.getId());
    assertEquals(transactionDto.getDate(), transactionEntity.getDate());
    assertEquals(transactionDto.getPrice(), transactionEntity.getPrice());
    assertEquals(transactionDto.getEarned(), transactionEntity.getEarned());
  }

  public static void equalTransactionTransactionEntity(Transaction transaction, TransactionEntity transactionEntity) {
    assertEquals(transaction.getDate(), transactionEntity.getDate());
    assertEquals(transaction.getPrice(), transactionEntity.getPrice());
    assertEquals(transaction.getEarned(), transactionEntity.getEarned());
    assertEquals(transaction.getCommodities().size(), transactionEntity.getCommodityEntities().size());
  }

  public static void equalCommodityCommodityEntity(Commodity commodity, CommodityEntity commodityEntity) {
    assertEquals(commodity.getProducer(), commodityEntity.getProducer());
    assertEquals(commodity.getOldPrice(), commodityEntity.getOldPrice());
    assertEquals(commodity.getNewPrice(), commodityEntity.getNewPrice());
    assertEquals(commodity.getGotTime(), commodityEntity.getGotTime());
    assertEquals(commodity.getSoldTime(), commodityEntity.getSoldTime());
    assertEquals(commodity.getImages().size(), commodityEntity.getImagesEntity().getImages().size());
  }

  public static void equalCommodityDtoCommodityEntity(CommodityDto commodityDto, CommodityEntity commodityEntity) {
    assertEquals(commodityDto.getId(), commodityEntity.getId());
    assertEquals(commodityDto.getProducer(), commodityEntity.getProducer());
    assertEquals(commodityDto.getOldPrice(), commodityEntity.getOldPrice());
    assertEquals(commodityDto.getNewPrice(), commodityEntity.getNewPrice());
    assertEquals(commodityDto.getGotTime(), commodityEntity.getGotTime());
    assertEquals(commodityDto.getSoldTime(), commodityEntity.getSoldTime());
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
