package be.kdg.sa.warehouse.handeler;

import be.kdg.sa.warehouse.config.RabbitTopology;
import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.service.delivery.CreateDeliveryService;
import be.kdg.sa.warehouse.service.delivery.UpdateDeliveryService;
import be.kdg.sa.warehouse.service.warehouse.UpdateWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FifoHandler {
    private final CreateDeliveryService createDeliveryService;
    private final UpdateDeliveryService updateDeliveryService;
    private final UpdateWarehouseService updateWarehouseService;
    private final Logger logger = LoggerFactory.getLogger(FifoHandler.class);

    public FifoHandler(CreateDeliveryService createDeliveryService, UpdateDeliveryService updateDeliveryService, UpdateWarehouseService updateWarehouseService) {
        this.createDeliveryService = createDeliveryService;
        this.updateDeliveryService = updateDeliveryService;
        this.updateWarehouseService = updateWarehouseService;
    }

    @RabbitListener(queues = RabbitTopology.DIRECT_QUEUE_FIFO_DELIVERY)
    public void receiveDelivery(DeliveryDto deliveryDto) {
        if (deliveryDto.getAmount() == null){
            logger.info("   Received FIFO Delivery: {} - {} - {}", deliveryDto.getDeliveryTime(), deliveryDto.getMaterialType(), deliveryDto.getWarehouseId());
            createDeliveryService.deliveryForWarehouse(deliveryDto);
        } else {
            logger.info("   Received FIFO material amount {} for warehouse: {}", deliveryDto.getAmount(), deliveryDto.getWarehouseId());
            updateDeliveryService.updateDeliveryByAmount(deliveryDto);
            updateWarehouseService.updateWarehouseWithDelivery(deliveryDto);
        }
    }
}
