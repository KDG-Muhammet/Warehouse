package be.kdg.sa.warehouse.service.delivery;

import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.domain.Delivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UpdateDeliveryService {
    private final DeliveryService deliveryService;
    private final Logger logger = LoggerFactory.getLogger(UpdateDeliveryService.class);

    public UpdateDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Transactional
    public void updateDeliveryByAmount(DeliveryDto deliveryDto) {
        logger.info("   Updating delivery for {}:{} by amount {}", deliveryDto.getDeliveryTime().getHour(), deliveryDto.getDeliveryTime().getMinute(),deliveryDto.getAmount());
        Delivery delivery = deliveryService.findDeliveryByDeliveryDate(deliveryDto.getDeliveryTime());
        delivery.setAmount(new BigDecimal(String.valueOf(deliveryDto.getAmount())));
    }
}
