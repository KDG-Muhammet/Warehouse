package be.kdg.sa.warehouse.service.delivery;

import be.kdg.sa.warehouse.domain.Delivery;
import be.kdg.sa.warehouse.repository.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }


    @Transactional(readOnly = true)
    public Delivery findDeliveryByDeliveryDate(LocalDateTime deliveryDate) {
        logger.info("   Finding delivery from {}", deliveryDate);
        return deliveryRepository.findDeliveryByDeliveryDate(deliveryDate);
    }


    @Transactional
    public void createDelivery(Delivery delivery) {
        logger.info("   {}:{} Delivery {} for {}", delivery.getDeliveryDate().getHour(), delivery.getDeliveryDate().getMinute(), delivery.getMaterialType(), delivery.getWarehouse().getId());
        deliveryRepository.save(delivery);
    }
}
