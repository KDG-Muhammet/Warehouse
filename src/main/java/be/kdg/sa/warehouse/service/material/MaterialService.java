package be.kdg.sa.warehouse.service.material;

import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import be.kdg.sa.warehouse.repository.MaterialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final Logger logger = LoggerFactory.getLogger(MaterialService.class);


    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Transactional(readOnly = true)
    public Material findMaterialByType(String materialType) {
        logger.info("Finding material by type: {}" ,materialType);
        return materialRepository.findMaterialByType(MaterialType.valueOf(materialType));
    }

    @Transactional(readOnly = true)
    public List<Material> findAllMaterials() {
        logger.info("Finding all materials");
        return materialRepository.findAll();
    }
}
