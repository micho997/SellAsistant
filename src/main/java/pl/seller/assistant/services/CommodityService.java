package pl.seller.assistant.services;

import static pl.seller.assistant.databases.EntityMapper.toBlob;
import static pl.seller.assistant.databases.EntityMapper.toEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.seller.assistant.databases.CommoditiesRepository;
import pl.seller.assistant.databases.EntityMapper;
import pl.seller.assistant.databases.ImagesRepository;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.models.Commodity;
import pl.seller.assistant.models.CommodityDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommodityService {

  private final CommoditiesRepository commoditiesRepository;
  private final ImagesRepository imagesRepository;

  public CommodityService(CommoditiesRepository commoditiesRepository, ImagesRepository imagesRepository) {
    this.commoditiesRepository = commoditiesRepository;
    this.imagesRepository = imagesRepository;
  }

  @Transactional
  public CommodityEntity save(Commodity commodity) {
    return commoditiesRepository.save(toEntity(commodity, imagesRepository.save(new ImagesEntity(toBlob(commodity.getImages())))));
  }

  @Transactional
  public Optional<CommodityDto> getById(Long id) {
    Optional<CommodityEntity> commodityEntity = commoditiesRepository.findById(id);
    return commodityEntity.map(EntityMapper::toDto);
  }

  public List<CommodityDto> getAll() {
    return commoditiesRepository.findAll().stream()
        .map(EntityMapper::toDto)
        .collect(Collectors.toList());
  }

  public List<CommodityDto> getByGotTime(LocalDate from, LocalDate to) {
    List<CommodityDto> result = new ArrayList<>();
    for (CommodityDto commodityDto : getAll()) {
      if (commodityDto.getGotTime().isAfter(from) && commodityDto.getGotTime().isBefore(to)) {
        result.add(commodityDto);
      }
    }
    return result;
  }

  public List<CommodityDto> getBySoldTime(LocalDate from, LocalDate to) {
    List<CommodityDto> result = new ArrayList<>();
    for (CommodityDto commodityDto : getAll()) {
      if (commodityDto.getSoldTime() != null) {
        if (commodityDto.getSoldTime().isAfter(from) && commodityDto.getSoldTime().isBefore(to)) {
          result.add(commodityDto);
        }
      }
    }
    return result;
  }
}
