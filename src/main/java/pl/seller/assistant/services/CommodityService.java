package pl.seller.assistant.services;

import static pl.seller.assistant.databases.EntityMapper.toEntity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.seller.assistant.databases.CommoditiesRepository;
import pl.seller.assistant.databases.ImagesRepository;
import pl.seller.assistant.databases.entity.CommodityEntity;
import pl.seller.assistant.databases.entity.ImagesEntity;
import pl.seller.assistant.models.Commodity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommodityService {

  private final CommoditiesRepository commoditiesRepository;
  private final ImagesRepository imagesRepository;

  @Transactional
  public CommodityEntity save(Commodity commodity) {
    ImagesEntity imagesEntities = imagesRepository.save(toEntity(commodity.getImages()));
    return commoditiesRepository.save(toEntity(commodity, imagesEntities));
  }

  @Transactional
  public Optional<CommodityEntity> getById(Long id) {
    return commoditiesRepository.findById(id);
  }

  public List<CommodityEntity> getByGotTime(LocalDate from, LocalDate to) {
    return commoditiesRepository.findAll().stream()
        .filter(commodity -> commodity.getGotTime().isAfter(from) && commodity.getGotTime().isBefore(to))
        .collect(Collectors.toList());
  }

  public List<CommodityEntity> getBySoldTime(LocalDate from, LocalDate to) {
    return commoditiesRepository.findAll().stream()
        .filter(commodity -> commodity.getSoldTime() != null)
        .filter(commodity -> commodity.getSoldTime().isAfter(from) && commodity.getSoldTime().isBefore(to))
        .collect(Collectors.toList());
  }
}
