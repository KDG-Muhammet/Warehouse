package be.kdg.sa.warehouse.handeler;

import be.kdg.sa.warehouse.config.RabbitTopology;
import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.service.delivery.CreateDeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeliveryHandler {
    private final CreateDeliveryService createDeliveryService;
    private final Logger logger = LoggerFactory.getLogger(DeliveryHandler.class);

    public DeliveryHandler(CreateDeliveryService createDeliveryService) {
        this.createDeliveryService = createDeliveryService;
    }

    @RabbitListener(queues = RabbitTopology.DIRECT_QUEUE_DELIVERY)
    public void receiveDelivery(DeliveryDto deliveryDto) {
        logger.info("   Received Delivery: {} - {} - {}", deliveryDto.getDeliveryTime(), deliveryDto.getMaterialType(), deliveryDto.getWarehouseId());
        createDeliveryService.deliveryForWarehouse(deliveryDto);
    }
}
