package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.rmq.SenderPdtDto;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UpdateWarehouseService {
    private final WarehouseService warehouseService;
    private final Logger logger = LoggerFactory.getLogger(UpdateWarehouseService.class);


    public UpdateWarehouseService(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Transactional
    public void updateWarehouseWithDelivery(SenderPdtDto senderPdtDto) {
        MaterialType materialType = getMaterialEnumType(senderPdtDto.getWarehouse());

        Warehouse warehouse = warehouseService.findWarehouseByMaterial_Type(materialType);
        warehouse.setOccupancy(senderPdtDto.getMaterialWeight());

        logger.info("Warehouse {} updated. Occupancy = {}", senderPdtDto.getWarehouse(), warehouse.getOccupancy());
    }

    private MaterialType getMaterialEnumType(String warehouse) {
        return Arrays.stream(MaterialType.values())
                .filter(m -> m.name().equalsIgnoreCase(warehouse))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown material type: " + warehouse));
    }
}
