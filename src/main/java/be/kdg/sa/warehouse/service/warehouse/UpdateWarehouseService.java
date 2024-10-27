package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.domain.Delivery;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static be.kdg.sa.warehouse.util.Calculation.calculateStoragePricePerDelivery;

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

            BigDecimal amountToDeduct = BigDecimal.valueOf(orderLine.getAmount() * convertToTon);
            BigDecimal remainingAmountToDeduct = amountToDeduct;

            List<Delivery> deliveries = warehouse.getDeliveries().stream()
                    .sorted(Comparator.comparing(Delivery::getDeliveryDate))
                    .toList();
            int count = 0;
            for (Delivery delivery : deliveries) {
                if (remainingAmountToDeduct.compareTo(BigDecimal.ZERO) <= 0) {
                    logger.info("   done calculating amount for deliveries : {} ", remainingAmountToDeduct);
                    break;
                }
                BigDecimal availableAmount = delivery.getAmount();

                if (availableAmount.compareTo(remainingAmountToDeduct) <= 0) {
                    remainingAmountToDeduct = remainingAmountToDeduct.subtract(availableAmount);
                    delivery.setAmount(BigDecimal.ZERO);
                    count++;
                    logger.info("   Used entire delivery {}. Remaining amount to deduct: {}",count, remainingAmountToDeduct);
                } else {
                    delivery.setAmount(availableAmount.subtract(remainingAmountToDeduct));
                    count++;
                    logger.info("   delivery {} had more amount then in orderline. used amount: {}. current amount of delevery: {}",count, remainingAmountToDeduct, delivery.getAmount());
                    remainingAmountToDeduct = BigDecimal.ZERO;
                }

                int daysStored = delivery.getDays();
                delivery.setCostPrice(calculateStoragePricePerDelivery(daysStored,delivery.getStoragePrice(),delivery.getAmount()));
            }

            BigDecimal availableStock = warehouse.getOccupancy();
            warehouse.setOccupancy(availableStock.subtract(amountToDeduct));
            logger.info("   lower warehouse Occupancy by  : {} ", amountToDeduct);
        }
    }
}
