package be.kdg.sa.warehouse.service.material;

import be.kdg.sa.warehouse.domain.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UpdateMaterialService {
    private final MaterialService materialService;
    private final Logger logger = LoggerFactory.getLogger(UpdateMaterialService.class);


    public UpdateMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }


    @Transactional
    public Material updateMaterialById(String materialType, BigDecimal sellingPrice, BigDecimal storagePrice){
        logger.info("Updating material with type: {}" ,materialType);
        return modelToObject(materialType, sellingPrice, storagePrice);
    }

    private Material modelToObject(String materialType, BigDecimal sellingPrice, BigDecimal storagePrice){
        Material material = materialService.findMaterialByType(materialType);
        material.setSellingPrice(sellingPrice);
        material.setStoragePrice(storagePrice);
        return material;
    }


}
