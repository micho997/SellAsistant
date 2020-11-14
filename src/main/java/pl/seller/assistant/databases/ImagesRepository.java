package pl.seller.assistant.databases;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.seller.assistant.databases.entity.ImagesEntity;

public interface ImagesRepository extends JpaRepository<ImagesEntity, Long> {

}
