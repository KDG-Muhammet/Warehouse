package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import be.kdg.sa.warehouse.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class CreateWarehouseService {
    private final WarehouseService warehouseService;
    private final MaterialService materialService;

    @Value("${spring.application.warehouse.capacity}")
    private BigDecimal capacity;

    @Value("${spring.application.warehouse.opacity}")
    private BigDecimal opacity;

    public CreateWarehouseService(WarehouseService warehouseService, MaterialService materialService) {
        this.warehouseService = warehouseService;
        this.materialService = materialService;
    }

    @Transactional
    public void createWarehousesForNewSeller(Seller newSeller) {
        Arrays.stream(MaterialType.values()).forEach(type -> {
            Material material = materialService.findMaterialByType(type.toString().toUpperCase());
            warehouseService.createWarehouse(new Warehouse(material, capacity, opacity, newSeller));
        });
    }




}
