package be.kdg.sa.warehouse.service.delivery;

import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.domain.Delivery;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.service.material.MaterialService;
import be.kdg.sa.warehouse.service.po.UpdatePurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static be.kdg.sa.warehouse.util.Calculation.calculateStoragePricePerDelivery;


@Service
public class UpdateDeliveryService {
    private final DeliveryService deliveryService;
    private final MaterialService materialService;
    private final UpdatePurchaseOrderService updatePurchaseOrderService;

    private final Logger logger = LoggerFactory.getLogger(UpdateDeliveryService.class);

    public UpdateDeliveryService(DeliveryService deliveryService, MaterialService materialService, UpdatePurchaseOrderService updatePurchaseOrderService) {
        this.deliveryService = deliveryService;
        this.materialService = materialService;
        this.updatePurchaseOrderService = updatePurchaseOrderService;
    }

    @Transactional
    public void updateDeliveryByAmount(DeliveryDto deliveryDto) {
        logger.info("   Updating delivery for {}:{} by amount {}", deliveryDto.getDeliveryTime().getHour(), deliveryDto.getDeliveryTime().getMinute(), deliveryDto.getAmount());
        Delivery delivery = deliveryService.findDeliveryByDeliveryDate(deliveryDto.getDeliveryTime());
        delivery.setAmount(new BigDecimal(String.valueOf(deliveryDto.getAmount())));

        Material material = materialService.findMaterialByType(String.valueOf(delivery.getMaterialType()));
        long days = ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), deliveryDto.getDeliveryTime().toLocalDate());
        BigDecimal CostPrice = calculateStoragePricePerDelivery((int) days, material.getSellingPrice(), delivery.getAmount());

        delivery.setStoragePrice(material.getStoragePrice());
        delivery.setDays((int) days);
        delivery.setCostPrice(CostPrice);
        updatePurchaseOrderService.checkPosOnHold();

    }

}
