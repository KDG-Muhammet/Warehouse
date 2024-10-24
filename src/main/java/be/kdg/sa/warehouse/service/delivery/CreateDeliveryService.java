package be.kdg.sa.warehouse.service.delivery;

import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.domain.Delivery;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.service.rest.SendPDTService;
import be.kdg.sa.warehouse.service.warehouse.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDeliveryService {
    private final DeliveryService deliveryService;
    private final WarehouseService warehouseService;
    private final SendPDTService sendPDTService;

    public CreateDeliveryService(DeliveryService deliveryService, WarehouseService warehouseService, SendPDTService sendPDTService) {
        this.deliveryService = deliveryService;
        this.warehouseService = warehouseService;
        this.sendPDTService = sendPDTService;
    }

    @Transactional
    public void deliveryForWarehouse(DeliveryDto deliveryDto) {
        Warehouse warehouse = warehouseService.findWarehouseById(deliveryDto.getWarehouseId());
        Delivery delivery = new Delivery(warehouse.getMaterial().getType(), deliveryDto.getDeliveryTime(), warehouse);

        deliveryService.createDelivery(delivery);
        sendPDTService.sendPDT(deliveryDto);
    }
}
