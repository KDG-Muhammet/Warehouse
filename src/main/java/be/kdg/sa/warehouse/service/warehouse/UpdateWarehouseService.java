package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.domain.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UpdateWarehouseService {
    private final WarehouseService warehouseService;
    private final Logger logger = LoggerFactory.getLogger(UpdateWarehouseService.class);


    public UpdateWarehouseService(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Transactional
    public void updateWarehouseWithDelivery(DeliveryDto deliveryDto) {
        Warehouse warehouse = warehouseService.findWarehouseById(deliveryDto.getWarehouseId());
        BigDecimal newWeight = warehouse.getOccupancy().add(deliveryDto.getAmount());
        warehouse.setOccupancy(newWeight);

        logger.info("   Warehouse {} updated. Occupancy = {}", warehouse.getMaterial().getType(), warehouse.getOccupancy());
    }
}
