package be.kdg.sa.warehouse.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeliveryDto {
    private LocalDateTime deliveryTime;
    private String materialType;
    private UUID warehouseId;

    public DeliveryDto() {}

    public DeliveryDto(LocalDateTime deliveryTime, String materialType, UUID warehouseId) {
        this.deliveryTime = deliveryTime;
        this.materialType = materialType;
        this.warehouseId = warehouseId;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }
}
