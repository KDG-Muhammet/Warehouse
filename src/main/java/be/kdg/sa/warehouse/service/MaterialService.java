package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import be.kdg.sa.warehouse.repository.MaterialRepository;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    private MaterialRepository materialRepository;


    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material findMaterialByType(String materialName) {
        return materialRepository.findMaterialByType(MaterialType.valueOf(materialName));
    }
}
