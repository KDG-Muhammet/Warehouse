package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.dto.MaterialDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import be.kdg.sa.warehouse.repository.MaterialRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final Logger logger = LoggerFactory.getLogger(MaterialService.class);
    private final ModelMapper modelMapper;


    public MaterialService(MaterialRepository materialRepository, ModelMapper modelMapper) {
        this.materialRepository = materialRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public Material findMaterialByType(String materialName) {
        logger.info("Finding material by type: {}" ,materialName);
        return materialRepository.findMaterialByType(MaterialType.valueOf(materialName));
    }

    @Transactional(readOnly = true)
    public List<MaterialDto> findAllMaterials() {
        logger.info("Finding all materials");
        return materialRepository.findAll().stream().map(m -> modelMapper.map(m, MaterialDto.class)).toList();
    }
}
