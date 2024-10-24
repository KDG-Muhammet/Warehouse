package be.kdg.sa.warehouse.handeler;

import be.kdg.sa.warehouse.config.RabbitTopology;
import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.service.delivery.UpdateDeliveryService;
import be.kdg.sa.warehouse.service.warehouse.UpdateWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MaterialHandler {
    private final UpdateDeliveryService updateDeliveryService;
    private final UpdateWarehouseService updateWarehouseService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MaterialHandler(UpdateDeliveryService updateDeliveryService, UpdateWarehouseService updateWarehouseService) {
        this.updateDeliveryService = updateDeliveryService;
        this.updateWarehouseService = updateWarehouseService;
    }

    @RabbitListener(queues = RabbitTopology.DIRECT_QUEUE_MATERIAL)
    public void receiveMaterialAmountForDelivery(DeliveryDto deliveryDto) {
        logger.info("   Received material amount {} for warehouse: {}", deliveryDto.getAmount(), deliveryDto.getWarehouseId());
        updateDeliveryService.updateDeliveryByAmount(deliveryDto);
        updateWarehouseService.updateWarehouseWithDelivery(deliveryDto);
    }
}
