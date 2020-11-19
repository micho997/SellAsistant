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

import java.util.Optional;

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
}
