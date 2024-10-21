package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.rmq.SenderPdtDto;
import be.kdg.sa.warehouse.domain.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UpdateWarehouseService {
    private final CreateWarehouseService createWarehouseService;
    private final Logger logger = LoggerFactory.getLogger(UpdateWarehouseService.class);


    public UpdateWarehouseService(CreateWarehouseService createWarehouseService) {
        this.createWarehouseService = createWarehouseService;
    }

    @Transactional
    public void updateWarehouseWithDelivery(SenderPdtDto senderPdtDto) {
        Warehouse warehouse = createWarehouseService.createOrUpdateWarehouse(senderPdtDto);
        BigDecimal newWeight = warehouse.getOccupancy().add(senderPdtDto.getMaterialWeight());
        warehouse.setOccupancy(newWeight);

        logger.info("   Warehouse {} updated. Occupancy = {}", senderPdtDto.getMaterialType(), warehouse.getOccupancy());
    }
}
