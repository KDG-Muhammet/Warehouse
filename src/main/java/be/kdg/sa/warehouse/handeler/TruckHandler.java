package be.kdg.sa.warehouse.handeler;


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

    @RabbitListener(queues = RabbitTopology.DIRECT_QUEUE_DELIVERY)
    public void handleSenderPdtDto(SenderPdtDto senderPdtDto) {
        logger.info("   Load for {} -> Amount = {}", senderPdtDto.getMaterialType(), senderPdtDto.getMaterialWeight());
        updateWarehouseService.updateWarehouseWithDelivery(senderPdtDto);
    }

    @RabbitListener(queues = RabbitTopology.DIRECT_QUEUE_MATERIAL)
    public void handleSenderPdtDtoFifo(SenderPdtDto senderPdtDto) {
        logger.info("   FIFO Load for {} -> Amount = {}", senderPdtDto.getMaterialType(), senderPdtDto.getMaterialWeight());
        updateWarehouseService.updateWarehouseWithDelivery(senderPdtDto);
    }
}
