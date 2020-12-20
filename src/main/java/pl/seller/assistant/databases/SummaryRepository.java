package pl.seller.assistant.databases;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.seller.assistant.databases.entity.SummaryEntity;

public interface SummaryRepository extends JpaRepository<SummaryEntity, String> {

}
