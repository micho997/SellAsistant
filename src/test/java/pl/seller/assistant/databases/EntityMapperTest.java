package pl.seller.assistant.databases;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static pl.seller.assistant.databases.EntityMapper.toBlob;
import static pl.seller.assistant.databases.EntityMapper.toBufferedImage;
import static pl.seller.assistant.databases.EntityMapper.toDto;
import static pl.seller.assistant.databases.EntityMapper.toEntity;
import static pl.seller.assistant.mother.CommodityMother.carlinSword;
import static pl.seller.assistant.mother.CommodityMother.entity;
import static pl.seller.assistant.mother.CommodityMother.pugSocks;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_USERNAME;
import static pl.seller.assistant.mother.ImageMother.exampleBufferedImage;
import static pl.seller.assistant.mother.SameObjectChecker.equalCommodityCommodityEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalCommodityDtoCommodityEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalSummarySummaryEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalTransactionDtoTransactionEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalTransactionTransactionEntity;
import static pl.seller.assistant.mother.SummaryMother.exampleSummary;
import static pl.seller.assistant.mother.TransactionMother.entity;
import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;
import static pl.seller.assistant.mother.TransactionMother.exampleTransactionWithEntry;

import org.junit.jupiter.api.Test;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.databases.entity.SummaryEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.services.summary.Summary;

import java.awt.image.BufferedImage;
import java.sql.Blob;
import java.util.List;

class EntityMapperTest {

  @Test
  void should_convert_commodity_to_commodityEntity() {
    // given
    Commodity commodity = pugSocks();

    // when
    CommodityEntity entity = toEntity(commodity, mock(ImagesEntity.class));

    // then
    equalCommodityCommodityEntity(commodity, entity);
  }

  @Test
  void should_convert_transaction_to_transactionEntity() {
    // given
    Transaction transaction = exampleTransactionWithEntry();

    // when
    TransactionEntity entity = toEntity(transaction, singletonList(mock(CommodityEntity.class)), EXAMPLE_USERNAME);

    // then
    equalTransactionTransactionEntity(transaction, entity);
    assertEquals(EXAMPLE_USERNAME, entity.getOwner());
  }

  @Test
  void should_convert_bufferedImages_to_imageEntity() {
    // given
    List<BufferedImage> images = singletonList(exampleBufferedImage());

    // when
    ImagesEntity imagesEntity = toEntity(images);

    // then
    assertEquals(images.size(), imagesEntity.getImages().size());
  }

  @Test
  void should_convert_summary_to_summaryEntity() {
    // given
    Summary summary = exampleSummary();

    // when
    SummaryEntity summaryEntity = toEntity(summary, EXAMPLE_USERNAME);

    // then
    equalSummarySummaryEntity(summary, summaryEntity);
    assertEquals(EXAMPLE_USERNAME, summaryEntity.getOwner());
  }

  @Test
  void should_convert_commodityEntity_to_commodityDto() {
    // given
    CommodityEntity commodityEntity = entity(carlinSword());

    // when
    CommodityDto commodityDto = toDto(commodityEntity);

    // then
    equalCommodityDtoCommodityEntity(commodityDto, commodityEntity);
  }

  @Test
  void should_convert_transactionEntity_to_transactionDto() {
    // given
    TransactionEntity transactionEntity = entity(exampleTransaction());

    // when
    TransactionDto transactionDto = toDto(transactionEntity);

    // then
    equalTransactionDtoTransactionEntity(transactionDto, transactionEntity);
  }

  @Test
  void should_convert_bufferedImage_to_blob_and_back() {
    // given
    BufferedImage bufferedImage = exampleBufferedImage();

    // when
    List<Blob> blob = toBlob(singletonList(bufferedImage));

    // then
    assertEquals(1, blob.size());
    assertEquals(1, toBufferedImage(blob).size());
  }
}
