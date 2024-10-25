package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UpdateWarehouseService {
    private final WarehouseService warehouseService;
    private final Logger logger = LoggerFactory.getLogger(UpdateWarehouseService.class);

    @Value("${convertToTon}")
    private long convertToTon;


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

    @Transactional
    public void updateWarehouse(PurchaseOrder purchaseOrder) {
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            Optional<Warehouse> warehouseOptional = warehouseService.findWarehouseBySellerUUIDAndMaterial_Id(purchaseOrder.getSeller().getUUID(), orderLine.getMaterial().getId());
            Warehouse warehouse = warehouseOptional.get();
            BigDecimal amountInTon = BigDecimal.valueOf(orderLine.getAmount() * convertToTon);
            BigDecimal availableStock = warehouse.getOccupancy();
            warehouse.setOccupancy(availableStock.subtract(amountInTon));
            logger.info(    "lower warehouse Occupancy by  : {} ", amountInTon);
        }
    }
}
