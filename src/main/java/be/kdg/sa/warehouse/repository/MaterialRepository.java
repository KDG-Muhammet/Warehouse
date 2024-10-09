package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MaterialRepository extends JpaRepository<Material, UUID> {

    Material findMaterialByType(MaterialType type);
}
