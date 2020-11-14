package pl.seller.assistant.databases;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.seller.assistant.databases.entity.CommodityEntity;

public interface CommoditiesRepository extends JpaRepository<CommodityEntity, Long> {

}
