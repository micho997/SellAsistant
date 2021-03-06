package pl.seller.assistant.databases;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class EntityMapper {

  public static TransactionEntity toEntity(Transaction transaction, List<CommodityEntity> commodityEntities, String owner) {
    return TransactionEntity.builder()
        .date(transaction.getDate())
        .price(transaction.getPrice().setScale(2, RoundingMode.CEILING))
        .earned(transaction.getEarned())
        .commodityEntities(commodityEntities)
        .owner(owner).build();
  }

  public static CommodityEntity toEntity(Commodity commodity, ImagesEntity imagesEntity) {
    return CommodityEntity.builder()
        .producer(commodity.getProducer())
        .oldPrice(commodity.getOldPrice().setScale(2, RoundingMode.CEILING))
        .newPrice(commodity.getNewPrice())
        .gotTime(commodity.getGotTime())
        .soldTime(commodity.getSoldTime())
        .imagesEntity(imagesEntity).build();
  }

  public static ImagesEntity toEntity(List<BufferedImage> images) {
    return ImagesEntity.builder()
        .images(toBlob(images)).build();
  }

  public static SummaryEntity toEntity(Summary summary, String owner) {
    return SummaryEntity.builder()
        .monthOfYear(summary.getMountOfYear())
        .owner(owner)
        .profit(summary.getProfit())
        .cost(summary.getCost())
        .profitMinusCost(summary.getProfitMinusCost())
        .boughtCommodities(summary.getBoughtCommodities())
        .soldCommodities(summary.getSoldCommodities())
        .mostPopularProducer(summary.getMostPopularProducer())
        .commodityWithHighestPriceId(summary.getCommodityWithHighestPrice().getId())
        .commodityWithHighestProfitId(summary.getCommodityWithHighestProfit().getId())
        .lastTransactionDateId(summary.getLastTransactionDate().getId()).build();
  }

  public static CommodityDto toDto(CommodityEntity entity) {
    return CommodityDto.builder()
        .id(entity.getId())
        .producer(entity.getProducer())
        .oldPrice(entity.getOldPrice().setScale(2, RoundingMode.CEILING))
        .newPrice(entity.getNewPrice())
        .gotTime(entity.getGotTime())
        .soldTime(entity.getSoldTime()).build();
  }

  public static TransactionDto toDto(TransactionEntity entity) {
    return TransactionDto.builder()
        .id(entity.getId())
        .date(entity.getDate())
        .price(entity.getPrice().setScale(2, RoundingMode.CEILING))
        .earned(entity.getEarned()).build();
  }

  public static List<Blob> toBlob(List<BufferedImage> images) {
    ByteArrayOutputStream converter = new ByteArrayOutputStream();

    return images.stream()
        .map(image -> {
          try {
            return ImageIO.write(image, "png", converter);
          } catch (IOException exception) {
            throw new RuntimeException("Mapping Exception: incorrect image format!");
          }
        })
        .map(image -> {
          try {
            return new SerialBlob(converter.toByteArray());
          } catch (SQLException throwables) {
            throw new RuntimeException("Can not convert image to database format.");
          }
        })
        .collect(Collectors.toList());
  }

  public static List<BufferedImage> toBufferedImage(List<Blob> images) {
    return images.stream()
        .map(blob -> {
          try {
            return blob.getBinaryStream();
          } catch (SQLException throwables) {
            throw new RuntimeException("Mapping Exception: incorrect image format!");
          }
        })
        .map(binary -> {
          try {
            return ImageIO.read(binary);
          } catch (IOException exception) {
            throw new RuntimeException("Can not convert image to database format.");
          }
        })
        .collect(Collectors.toList());
  }
}
