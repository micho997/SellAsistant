package pl.seller.assistant.databases;

import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.CommodityEntity.CommodityEntityBuilder;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.databases.entity.TransactionEntity;
import pl.seller.assistant.databases.entity.TransactionEntity.TransactionEntityBuilder;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.CommodityDto;
import pl.seller.assistant.models.CommodityDto.CommodityDtoBuilder;
import pl.seller.assistant.models.Transaction;
import pl.seller.assistant.models.TransactionDto;
import pl.seller.assistant.models.TransactionDto.TransactionDtoBuilder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class EntityMapper {

  public static CommodityEntity toEntity(Commodity commodity, ImagesEntity imagesEntity) {
    return CommodityEntityBuilder.anCommodity()
        .producer(commodity.getProducer())
        .price(commodity.getPrice())
        .currentPrice(commodity.getCurrentPrice())
        .gotTime(commodity.getGotTime())
        .soldTime(commodity.getSoldTime())
        .imageId(imagesEntity).build();
  }

  public static CommodityEntity toEntity(CommodityDto commodityDto, ImagesEntity imagesEntity) {
    return CommodityEntityBuilder.anCommodity()
        .producer(commodityDto.getProducer())
        .price(commodityDto.getPrice())
        .currentPrice(commodityDto.getCurrentPrice())
        .gotTime(commodityDto.getGotTime())
        .soldTime(commodityDto.getSoldTime())
        .imageId(imagesEntity).build();
  }

  public static TransactionEntity toEntity(Transaction transaction, List<CommodityEntity> commodityEntities) {
    return TransactionEntityBuilder.anTransaction()
        .date(transaction.getDate())
        .price(transaction.getPrice())
        .earned(transaction.getEarned())
        .commodities(commodityEntities).build();
  }

  public static TransactionEntity toEntity(TransactionDto transactionDto, List<CommodityEntity> commodityEntities) {
    return TransactionEntityBuilder.anTransaction()
        .date(transactionDto.getDate())
        .price(transactionDto.getPrice())
        .earned(transactionDto.getEarned())
        .commodities(commodityEntities).build();
  }

  public static CommodityDto toDto(CommodityEntity entity) {
    return CommodityDtoBuilder.anCommodity()
        .producer(entity.getProducer())
        .price(entity.getPrice())
        .currentPrice(entity.getCurrentPrice())
        .gotTime(entity.getGotTime())
        .soldTime(entity.getSoldTime())
        .imageId(entity.getImageId().getId()).build();
  }

  public static TransactionDto toDto(TransactionEntity entity) {
    return TransactionDtoBuilder.anTransaction()
        .id(entity.getId())
        .date(entity.getDate())
        .price(entity.getPrice())
        .earned(entity.getEarned())
        .commodityIds(entity.getCommodities().stream()
            .map(CommodityEntity::getId)
            .collect(Collectors.toList())).build();
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