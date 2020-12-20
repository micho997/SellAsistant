package pl.seller.assistant.databases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.seller.assistant.databases.EntityMapper.toBlob;
import static pl.seller.assistant.databases.EntityMapper.toBufferedImage;
import static pl.seller.assistant.databases.EntityMapper.toDto;
import static pl.seller.assistant.databases.EntityMapper.toEntity;
import static pl.seller.assistant.mother.CommodityMother.carlinSword;
import static pl.seller.assistant.mother.CommodityMother.dto;
import static pl.seller.assistant.mother.CommodityMother.entity;
import static pl.seller.assistant.mother.CommodityMother.pugSocks;
import static pl.seller.assistant.mother.SameObjectChecker.equalCommodityCommodityEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalCommodityDtoCommodityEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalSummarySummaryEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalTransactionDtoTransactionEntity;
import static pl.seller.assistant.mother.SameObjectChecker.equalTransactionTransactionEntity;
import static pl.seller.assistant.mother.SummaryMother.exampleSummary;
import static pl.seller.assistant.mother.TransactionMother.dto;
import static pl.seller.assistant.mother.TransactionMother.entity;
import static pl.seller.assistant.mother.TransactionMother.exampleTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.databases.entity.SummaryEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.mother.CommodityMother;
import pl.seller.assistant.services.summary.Summary;

import java.awt.image.BufferedImage;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class EntityMapperTest {

  private static final String EXAMPLE_OWNER = "TEST_USER";

  private List<CommodityEntity> exampleCommodityEntities;
  private ImagesEntity exampleImageEntity;
  private Long exampleImageId;
  private List<Long> exampleCommodityIds;

  @BeforeEach
  void setUp() {
    exampleImageId = 5L;
    exampleCommodityIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
    Commodity commodity = carlinSword();
    exampleImageEntity = ImagesEntity.builder()
        .images(toBlob(commodity.getImages())).build();
    exampleCommodityEntities = Collections.singletonList(entity(commodity, exampleImageEntity));
  }

  @Test
  void should_convert_commodity_to_commodityEntity() {
    // given
    Commodity commodity = CommodityMother.pugSocks();

    // when
    CommodityEntity entity = toEntity(commodity, exampleImageEntity);

    // then
    equalCommodityCommodityEntity(commodity, entity);
  }

  @Test
  void should_convert_commodityDto_to_commodityEntity() {
    // given
    CommodityDto commodityDto = dto(carlinSword(), exampleImageId);

    // when
    CommodityEntity commodityEntity = toEntity(commodityDto, exampleImageEntity);

    // then
    equalCommodityDtoCommodityEntity(commodityDto, commodityEntity);
  }

  @Test
  void should_convert_transaction_to_transactionEntity() {
    // given
    Transaction transaction = exampleTransaction();
    Commodity commodity = carlinSword();
    transaction.setCommodities(Collections.singletonList(commodity));

    // when
    TransactionEntity entity = toEntity(transaction, exampleCommodityEntities);

    // then
    equalTransactionTransactionEntity(transaction, entity);
  }

  @Test
  void should_convert_transactionDto_to_transactionEntity() {
    // given
    TransactionDto transactionDto = dto(exampleTransaction(), exampleCommodityIds);

    // when
    TransactionEntity transactionEntity = toEntity(transactionDto, exampleCommodityEntities);

    // then
    equalTransactionDtoTransactionEntity(transactionDto, transactionEntity);
  }

  @Test
  void should_convert_bufferedImages_to_imageEntity() {
    // given
    List<BufferedImage> images = pugSocks().getImages();

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
    SummaryEntity summaryEntity = toEntity(summary, EXAMPLE_OWNER);

    // then
    equalSummarySummaryEntity(summary, summaryEntity);
  }

  @Test
  void should_convert_commodityEntity_to_commodityDto() {
    // given
    CommodityEntity commodityEntity = entity(carlinSword(), exampleImageEntity);

    // when
    CommodityDto commodityDto = toDto(commodityEntity);

    // then
    equalCommodityDtoCommodityEntity(commodityDto, commodityEntity);
  }

  @Test
  void should_convert_transactionEntity_to_transactionDto() {
    // given
    TransactionEntity transactionEntity = entity(exampleTransaction(), exampleCommodityEntities);

    // when
    TransactionDto transactionDto = toDto(transactionEntity);

    // then
    equalTransactionDtoTransactionEntity(transactionDto, transactionEntity);
  }

  @Test
  void should_convert_bufferedImage_to_blob_and_back() {
    // given
    BufferedImage bufferedImage = carlinSword().getImages().get(0);

    // when
    List<Blob> blob = toBlob(Collections.singletonList(bufferedImage));

    // then
    assertEquals(1, blob.size());
    assertEquals(1, toBufferedImage(blob).size());
  }
}
