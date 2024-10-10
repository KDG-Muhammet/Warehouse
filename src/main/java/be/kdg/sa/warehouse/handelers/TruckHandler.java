package be.kdg.sa.warehouse.handelers;


import be.kdg.sa.warehouse.config.RabbitTopology;
import be.kdg.sa.warehouse.controller.dto.rmq.SenderPdtDto;
import be.kdg.sa.warehouse.service.warehouse.UpdateWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TruckHandler {
    private final UpdateWarehouseService updateWarehouseService;
    private final Logger logger = LoggerFactory.getLogger(TruckHandler.class);

    public TruckHandler(UpdateWarehouseService updateWarehouseService) {
        this.updateWarehouseService = updateWarehouseService;
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_TRUCK)
    public void handleSenderPdtDto(SenderPdtDto senderPdtDto) {
        logger.info("Load for {} -> Amount = {}", senderPdtDto.getWarehouse(), senderPdtDto.getMaterialWeight());
        updateWarehouseService.updateWarehouseWithDelivery(senderPdtDto);
    }
}
